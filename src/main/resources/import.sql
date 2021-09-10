INSERT INTO CATEGORY (ID, NAME) VALUES (0, 'default'), (1, 'special');
INSERT INTO USER (USERNAME, ADMIN, DEACTIVATED, PASSWORD) VALUES
                                                              ('admin', true, false, '123'),
                                                              ('user1', false, false, '123'),
                                                              ('user2', false, false, '123');
INSERT INTO ENTRY (ID, CHECKIN, CHECKOUT, CATEGORY_ID, USER_USERNAME) VALUES
                                                                          (0, '2021-09-16 23:23:23', '2031-09-16 07:59:00', 0, 'user1'),
                                                                          (1, '2021-03-16 10:29:00', '2032-09-16 07:59:00', 0, 'admin'),
                                                                          (2, '2021-01-16 10:29:00', '2041-09-16 07:59:00', 1, 'user2'),
                                                                          (3, '1990-12-16 10:29:00', '2000-09-16 07:59:00', 1, 'user2'),
                                                                          (4, '2021-03-16 10:29:00', '2023-09-16 07:59:00', 0, 'user2'),
                                                                          (5, '2021-09-11 10:29:00', '2024-09-16 07:59:00', 1, 'user1'),
                                                                          (6, '2021-09-16 10:19:00', '2022-09-16 07:59:00', NULL, 'user1'),
                                                                          (7, '2021-09-16 10:39:00', '2023-09-16 07:59:00', 0, 'user2'),
                                                                          (8, '2021-09-16 03:29:00', '2023-09-16 07:59:00', 1, 'user2'),
                                                                          (9, '2021-09-16 10:29:00', '2024-09-16 07:59:00', 1, 'user2'),
                                                                          (10, '2021-09-16 10:29:00', '2041-09-16 07:59:00', 0, 'admin'),
                                                                          (11, '2021-09-16 10:29:00', '2042-09-16 07:59:00', NULL, 'admin'),
                                                                          (12, '2021-09-17 10:29:00', '2042-12-16 07:18:00', NULL, 'user1');