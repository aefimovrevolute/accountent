-- DROP TABLE sender_channel;
CREATE TABLE sender_channel
(
  id    BIGINT    NOT NULL CONSTRAINT sender_channel_pkey PRIMARY KEY,
  name  VARCHAR(500) NOT NULL,
  description VARCHAR(1000) NOT NULL,
  enabled BOOLEAN NOT NULL DEFAULT FALSE,
  sender_id BIGINT,
  CONSTRAINT sender_channel_sender_id FOREIGN KEY (sender_id)
  REFERENCES sender (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);
ALTER TABLE sender_channel
  OWNER TO notifier_user;
