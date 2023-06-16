-- Wstawienie nowego użytkownika do tabeli users
INSERT INTO users(user_id, avatarid, login, password, role)
VALUES ('user123', 'avatar123', 'user_login', 'user_password', 'USER_ROLE');

-- Wstawienie nowego pokoju czatu do tabeli chat_rooms
INSERT INTO chat_rooms(room_id, creation_date, description, name, picture)
VALUES ('room123', CURRENT_TIMESTAMP, 'Opis pokoju', 'Nazwa pokoju', 'URL_obrazka_pokoju');

-- Wstawienie nowej wiadomości do tabeli messages
INSERT INTO messages(message_id, content, creation_date, chat_room_id, user_id)
VALUES ('msg123', 'Treść wiadomości', CURRENT_TIMESTAMP, 'room123', 'user123');

-- Wstawienie nowego powiązania pomiędzy użytkownikiem a pokojem czatu do tabeli user_chatroom
INSERT INTO user_chatroom(user_id, room_id)
VALUES ('user123', 'room123');
