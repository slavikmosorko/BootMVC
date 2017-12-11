ALTER TABLE messages
  DROP COLUMN deleted;
ALTER TABLE messages
  DROP COLUMN addressee
ALTER TABLE messages
  DROP COLUMN subject;
ALTER TABLE users
  DROP COLUMN activation_code;