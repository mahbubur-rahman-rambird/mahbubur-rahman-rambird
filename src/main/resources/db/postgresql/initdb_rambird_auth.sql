-- psql -U pgadmin template1
-- CREATE USER itxxxx CREATEDB ENCRYPTED PASSWORD 'itxxxx';

--createdb -O rambird  -T template0 rambird-auth

-- ALTER ROLE rambird WITH LOGIN;

-- Table: myoath

-- DROP TABLE myoath;

CREATE TABLE myoath
(
  user_name character varying(100),
  password character varying(100),
  home character varying(20),
  full_name character varying(100),
  timezone character varying(100),
  last_login timestamp with time zone,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);

-- Table: user_roles

-- DROP TABLE user_roles;

CREATE TABLE user_roles
(
  user_role_id serial NOT NULL,
  username character varying(45) NOT NULL,
  "ROLE" character varying(45) NOT NULL,
  user_role character varying(50) DEFAULT 'ROLE_ADMIN'::character varying,
  CONSTRAINT user_roles_pkey PRIMARY KEY (user_role_id)
)
WITH (
  OIDS=FALSE
);