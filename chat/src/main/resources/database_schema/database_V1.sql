CREATE TABLE chat_rooms(
  room_id varchar(255) not null  PRIMARY KEY,
  creation_date timestamp(6),
  description varchar(255),
  name varchar(255),
  picture varchar(255)
);
create table users (
  user_id varchar(255),
  avatarid varchar(255),
  login varchar(255),
  password varchar(255),
  role varchar(255),
  PRIMARY KEY (user_id)
);
CREATE TABLE messages (
  message_id varchar(255) not null  PRIMARY KEY,
  content varchar(255),
  creation_date timestamp(6),
  chat_room_id varchar(255),
  user_id varchar(255),
  FOREIGN KEY (chat_room_id) REFERENCES chat_rooms (room_id),
  FOREIGN KEY (user_id) REFERENCES users (user_id)
);
CREATE TABLE user_chatroom (
  user_id VARCHAR(255),
  room_id VARCHAR(255),
  PRIMARY KEY (user_id, room_id),
  FOREIGN KEY (user_id) REFERENCES users (user_id),
  FOREIGN KEY (room_id) REFERENCES chat_rooms (room_id)
);












