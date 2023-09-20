CREATE TABLE user (
  id              BIGINT       NOT NULL AUTO_INCREMENT,
  first_name      VARCHAR(50)  NOT NULL,
  last_name       VARCHAR(50)  NOT NULL,
  email           VARCHAR(255) NOT NULL,
  password        VARCHAR(255) NOT NULL,
  role            VARCHAR(50)  NOT NULL,
  profile_picture MEDIUMBLOB   NOT NULL,
  created_at      TIMESTAMP    NOT NULL DEFAULT NOW(),
  updated_at      TIMESTAMP    NOT NULL DEFAULT NOW() ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT uc_user_email UNIQUE (email)
);

CREATE TABLE message (
  id          BIGINT     NOT NULL AUTO_INCREMENT,
  sender_id   BIGINT     NOT NULL,
  receiver_id BIGINT     NOT NULL,
  body        MEDIUMTEXT NOT NULL,
  is_seen     TINYINT    NOT NULL,
  is_deleted  TINYINT    NOT NULL,
  created_at  TIMESTAMP  NOT NULL DEFAULT NOW(),
  updated_at  TIMESTAMP  NOT NULL DEFAULT NOW() ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT sender FOREIGN KEY (sender_id) REFERENCES user (id),
  CONSTRAINT receiver FOREIGN KEY (receiver_id) REFERENCES user (id)
);

CREATE TABLE post (
  id         BIGINT       NOT NULL AUTO_INCREMENT,
  created_by BIGINT       NOT NULL,
  uri        VARCHAR(255) NOT NULL,
  title      TEXT         NOT NULL,
  body       MEDIUMTEXT   NOT NULL,
  access     BIGINT       NOT NULL,
  picture    MEDIUMBLOB   NOT NULL,
  created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP    NOT NULL DEFAULT NOW() ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT uc_post_uri UNIQUE (uri)
);

CREATE TABLE category (
  id         BIGINT    NOT NULL AUTO_INCREMENT,
  name       TEXT      NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW() ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (
    id
  )
);

CREATE TABLE comment (
  id         BIGINT    NOT NULL AUTO_INCREMENT,
  user_id    BIGINT    NOT NULL,
  post_id    BIGINT    NOT NULL,
  body       TEXT      NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW() ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT post_comment FOREIGN KEY (post_id) REFERENCES post (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  PRIMARY KEY (
    id
  )
);

CREATE TABLE post_category (
  post_id BIGINT(20) NOT NULL,
  category_id BIGINT(20) NOT NULL,
  KEY fk_category_id (category_id),
  KEY fk_post_id (post_id),
  CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category (id),
  CONSTRAINT fk_post_id FOREIGN KEY (post_id) REFERENCES post (id)
);

ALTER TABLE post ADD CONSTRAINT fk_post_created_by FOREIGN KEY (created_by)
REFERENCES user (id);

ALTER TABLE post ADD CONSTRAINT fk_post_comment_id FOREIGN KEY (comment_id)
REFERENCES comment (id);

ALTER TABLE message ADD CONSTRAINT fk_message_sender_id FOREIGN KEY (sender_id)
REFERENCES user (id);

ALTER TABLE message ADD CONSTRAINT fk_message_receiver_id FOREIGN KEY (receiver_id)
REFERENCES user (id);

ALTER TABLE comment ADD CONSTRAINT fk_comment_user_id FOREIGN KEY (user_id)
REFERENCES user (id);