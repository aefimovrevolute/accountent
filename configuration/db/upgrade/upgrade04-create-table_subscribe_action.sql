-- DROP TABLE subscribe_action;
CREATE TABLE subscribe_action
(
  id                BIGINT       NOT NULL CONSTRAINT subscribe_action_pkey PRIMARY KEY,
  subscriber        VARCHAR(500) NOT NULL,
  sender_id         BIGINT,
  sender_channel_id BIGINT,
  CONSTRAINT subscribe_action_sender_id FOREIGN KEY (sender_id)
  REFERENCES sender (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT subscribe_action_sender_channel_id FOREIGN KEY (sender_channel_id)
  REFERENCES sender_channel (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);
ALTER TABLE subscribe_action
  OWNER TO notifier_user;
