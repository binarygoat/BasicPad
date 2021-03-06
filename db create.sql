
-- create note table
CREATE TABLE NOTES_TABLE 
(
	NOTE_ID integer primary key autoincrement,
	NOTE_TITLE text not null,
	NOTE_CREATED text not null,
	NOTE_MODIFIED text not null
); 


-- create noteBody table
CREATE TABLE NOTE_BODIES_TABLE
(
	NOTE_ID integer,
	NOTE_BODY text,
	foreign key (NOTE_ID) references NOTES_TABLE (NOTE_ID)
);

