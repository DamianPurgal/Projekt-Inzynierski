CREATE TABLE Blackboards(
    ID BIGSERIAL PRIMARY KEY,
    link_id varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    color varchar(255) NOT NULL
);

CREATE TABLE Blackboard_Columns(
     ID BIGSERIAL PRIMARY KEY,
     Blackboard_id integer NOT NULL,
     name varchar(255) NOT NULL,
     color varchar(255) NOT NULL,
     position integer NOT NULL
);

CREATE TABLE tickets(
    ID BIGSERIAL PRIMARY KEY,
    Column_id integer NOT NULL,
    User_id bigint NOT NULL,
    name varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    color varchar(255) NOT NULL,
    position integer NOT NULL
);

CREATE TABLE comments(
    ID BIGSERIAL PRIMARY KEY,
    ticket_id integer NOT NULL,
    author_id bigint NOT NULL,
    text varchar(255) NOT NULL,
    date date NOT NULL
);

CREATE TABLE blackboard_contributors(
    ID BIGSERIAL PRIMARY KEY,
    User_id bigint NOT NULL,
    Blackboard_id bigint NOT NULL,
    role varchar(255) NOT NULL
);

ALTER TABLE Blackboard_Columns
    ADD CONSTRAINT Columns_Blackboard_id_fkey
        FOREIGN KEY (Blackboard_id) REFERENCES Blackboards (id);

ALTER TABLE tickets
    ADD CONSTRAINT tickets_User_id_fkey
        FOREIGN KEY (User_id) REFERENCES Users (id);

ALTER TABLE tickets
    ADD CONSTRAINT tickets_Column_id_fkey
        FOREIGN KEY (Column_id) REFERENCES Blackboard_Columns (id);

ALTER TABLE comments
    ADD CONSTRAINT comments_author_id_fkey
        FOREIGN KEY (author_id) REFERENCES Users (id);

ALTER TABLE comments
    ADD CONSTRAINT comments_ticket_id_fkey
        FOREIGN KEY (ticket_id) REFERENCES tickets (id);

ALTER TABLE blackboard_contributors
    ADD CONSTRAINT blackboard_contributors_User_id_fkey
        FOREIGN KEY (User_id) REFERENCES Users (id);

ALTER TABLE blackboard_contributors
    ADD CONSTRAINT blackboard_contributors_Blackboard_id_fkey
        FOREIGN KEY (Blackboard_id) REFERENCES Blackboards (id);
