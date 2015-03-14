
-- Table: mymiles
--
CREATE TABLE mymiles (
  mileid serial,
  catg varchar(20) default null,
  milestone text NOT NULL,
  done boolean DEFAULT 'false',
  date_created timestamptz default now(),
  date_done timestamptz,
  finish_by timestamptz default now(),
  search_index tsvector,
  comments text,
  review_date date,
  priority varchar(3) default '0N',
  user_name varchar(250) not null,
  home varchar(20),
  CONSTRAINT mymiles_pkey PRIMARY KEY (mileid),
  CONSTRAINT mymiles_priority_enum_check CHECK (priority IN ('0N', '1U', '2I', '3UI'))
);
-- Table: category
--
CREATE TABLE category (
  catgid serial,
  catg text NOT NULL,
  catg_label text NOT NULL,
  catg_rank int not null,
  user_name varchar(250) not null,
  home varchar(20),
  CONSTRAINT category_pkey PRIMARY KEY (catgid)
);

