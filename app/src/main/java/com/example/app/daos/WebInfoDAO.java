package com.example.app.daos;

import com.example.app.models.WebInfoResponse;
import com.example.utils.hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class WebInfoDAO implements IWebInfoDAO {

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = true)
    public List<WebInfoResponse> getInfoForUser(String userId) {
        String nativeQuery = "" +
                "SELECT " +
                "wi.id as id, " +
                "wi.message as message, " +
                "wi.title as title, " +
                "wi.user_id_from as userIdFrom " +
                "FROM web_info as wi " +
                "WHERE wi.user_id_to = :userId " +
                "AND wi.received = 0";
        Session session = HibernateUtil.getSession();
        Query query = session
                .createSQLQuery(nativeQuery)
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("message", StandardBasicTypes.STRING)
                .addScalar("title", StandardBasicTypes.STRING)
                .addScalar("userIdFrom", StandardBasicTypes.STRING)
                .setParameter("userId", userId)
                .setResultTransformer(Transformers.aliasToBean(WebInfoResponse.class));
        List list = query.list();
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void receiveMessagesForUser(List<WebInfoResponse> messages) {
        if (messages.size() > 0) {
            String ids = "";
            for (WebInfoResponse message : messages) {
                ids += "'" + message.getId() + "',";
            }
            if (ids != null && ids.length() > 0) {
                ids = ids.substring(0, ids.length() - 1);
            }
            String nativeQuery = "UPDATE web_info " +
                    "SET received = 1 " +
                    "WHERE id IN (" + ids + ")";
            Session session = HibernateUtil.getSession();
            Query query = session
                    .createSQLQuery(nativeQuery);
            query.executeUpdate();
        }
    }
}
