-- You can use this file to load seed data into the database using SQL statements
-- After successfuly testing a new functinality:
-- 	1. sudo su - postgres
--  2. pg_dump --column-inserts  wishop > /tmp/import.sql
--  3. copy the sql INSERT INTO statements and paste them here





--
-- Data for Name: audit_log_record; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO audit_log_record (id, created, entity_class, entity_id, message, user_id) VALUES (1, '2013-04-07 18:29:17.519', 'com.wishop.model.User', 1, 'AUDIT - Object com.wishop.model.User:1 saved by -1', -1);
INSERT INTO audit_log_record (id, created, entity_class, entity_id, message, user_id) VALUES (2, '2013-04-07 18:29:19.55', 'com.wishop.model.User', 2, 'AUDIT - Object com.wishop.model.User:2 saved by -1', -1);
INSERT INTO audit_log_record (id, created, entity_class, entity_id, message, user_id) VALUES (3, '2013-04-07 18:33:40.379', 'com.wishop.model.User', 3, 'AUDIT - Object com.wishop.model.User:3 saved by -1', -1);


--
-- Data for Name: hibernate_sequences; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO hibernate_sequences (sequence_name, sequence_next_hi_value) VALUES ('users', 4);
INSERT INTO hibernate_sequences (sequence_name, sequence_next_hi_value) VALUES ('audit_log_record', 4);


--
-- Data for Name: sku; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: sku_wishlists; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO users (id, creation_timestamp, creator_user_id, modification_timestamp, modifier_user_id, deleted, account_active, address_line1, address_line2, address_line3, city, country, county, postcode, date_of_birth, email, fax, first_name, last_name, mobile, password, profile, telephone, title) VALUES (1, '2013-04-07 18:29:17.519', -1, '2013-04-07 18:29:17.519', -1, false, true, '4 Wensleydale Drive', NULL, NULL, 'Leeds', 'UK', 'West Yorkshire', 'LS12 2HU', '1983-12-05 00:00:00', 'paulo.from.portugal@gmail.com', NULL, 'Paulo', 'Monteiro', '07590541213', '3fb5fce3c24aa7e9d1a01dc1aa43abf3', '35758', NULL, 'Mr');
INSERT INTO users (id, creation_timestamp, creator_user_id, modification_timestamp, modifier_user_id, deleted, account_active, address_line1, address_line2, address_line3, city, country, county, postcode, date_of_birth, email, fax, first_name, last_name, mobile, password, profile, telephone, title) VALUES (2, '2013-04-07 18:29:19.55', -1, '2013-04-07 18:29:19.55', -1, false, true, '2 Winston House, Endsleigh St', NULL, NULL, 'London', 'UK', 'London', 'WC1H 0EA', '1985-12-06 00:00:00', 'tran.k.hoang@gmail.com', NULL, 'Hoang', 'Tran', '1231231321323', 'f284baa8483b93f498aec72cc43be5b0', '35759', NULL, NULL);
INSERT INTO users (id, creation_timestamp, creator_user_id, modification_timestamp, modifier_user_id, deleted, account_active, address_line1, address_line2, address_line3, city, country, county, postcode, date_of_birth, email, fax, first_name, last_name, mobile, password, profile, telephone, title) VALUES (3, '2013-04-07 18:33:40.379', -1, '2013-04-07 18:33:40.379', -1, false, true, 'Test Address', NULL, NULL, 'London', 'UK', 'London', 'SE1', '2014-12-01 00:00:00', 'test.user@mailinator.com', NULL, 'Test', 'User', '1231231231', '0dcf2102d8bc6d598cbc6cf2892cffc3', '35833', NULL, 'Mr');


--
-- Data for Name: users_list_of_wishlists; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: wishlist; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: wishlist_sku_list; Type: TABLE DATA; Schema: public; Owner: postgres
--

