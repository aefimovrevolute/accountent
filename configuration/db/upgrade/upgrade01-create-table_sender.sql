CREATE TABLE sender
(
  id    BIGINT       NOT NULL CONSTRAINT sender_pkey PRIMARY KEY,
  name  VARCHAR(500) NOT NULL,
  email VARCHAR(150) NOT NULL,
  state VARCHAR(150) NOT NULL
)
WITH (
OIDS = FALSE
);
ALTER TABLE sender
  OWNER TO notifier_user;
