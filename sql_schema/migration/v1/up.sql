ALTER TABLE messages
  ADD COLUMN deleted BIT;
ALTER TABLE messages
  ADD COLUMN addressee VARCHAR(255);