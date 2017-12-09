ALTER TABLE messages
  ADD COLUMN deleted BIT;
ALTER TABLE messages
  ADD COLUMN addressee VARCHAR(255);
ALTER TABLE messages
  ADD COLUMN subject VARCHAR(255);