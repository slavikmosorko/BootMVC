package com.example.app.daos;

import com.example.app.models.GrantedAuthorityDTO;
import com.example.app.models.UserAccountDTO;
import com.example.utils.hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LogInDAO implements ILogInDAO {
    private static final String DEF_GROUP_AUTHORITIES_BY_USER_ID_QUERY = "" +
            "select distinct g.id as id, g.group_name as groupName, ga.authority as authority "
            + "from groups g, group_members gm, group_authorities ga "
            + "where gm.user_id = :userId " + "and g.id = ga.group_id "
            + "and g.id = gm.group_id";

    @Override
    public List<UserAccountDTO> loadUsersByUsername(String username) {
        String nativeQuery = "" +
                "SELECT " +
                "u.id as id, " +
                "u.int_id as intId, " +
                "u.username as username, " +
                "u.password as password, " +
                "u.activation_code as activationCode, " +
                "u.enabled as enabled " +
                "FROM users as u " +
                "WHERE u.username = :username";
        Session session = HibernateUtil.getSession();
        Query query = session
                .createSQLQuery(nativeQuery)
                .addScalar("id", StandardBasicTypes.STRING)
                .addScalar("intId", StandardBasicTypes.LONG)
                .addScalar("username", StandardBasicTypes.STRING)
                .addScalar("password", StandardBasicTypes.STRING)
                .addScalar("activationCode", StandardBasicTypes.STRING)
                .addScalar("enabled", StandardBasicTypes.BOOLEAN)
                .setParameter("username", username)
                .setResultTransformer(Transformers.aliasToBean(UserAccountDTO.class));
        List list = query.list();
        return list;
    }

    @Override
    public List<GrantedAuthorityDTO> loadUserAuthoritiesByUserId(String userId) {
        String nativeQuery = DEF_GROUP_AUTHORITIES_BY_USER_ID_QUERY;
        Session session = HibernateUtil.getSession();
        Query query = session
                .createSQLQuery(nativeQuery)
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("groupName", StandardBasicTypes.STRING)
                .addScalar("authority", StandardBasicTypes.STRING)
                .setParameter("userId", userId)
                .setResultTransformer(Transformers.aliasToBean(GrantedAuthorityDTO.class));
        List list = query.list();
        return list;
    }
}
