CREATE TABLE user(
  id BIGSERIAL PRIMARY KEY,
  user_name VARCHAR(20) NOT NULL,
  password_encoded VARCHAR(32) NOT NULL);