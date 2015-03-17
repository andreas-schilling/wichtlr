create table PARTICIPANTS (
    PAR_ID number(38) NOT NULL,
    PAR_NAME varchar2(255 char) not null,
    PAR_EMAIL varchar2(1024 char) not null
);

ALTER TABLE PARTICIPANTS ADD CONSTRAINT PAR_PK PRIMARY KEY ( PAR_ID ) ;

create sequence PAR_SEQ start with 1 nocache;

ALTER TABLE PARTICIPANTS
  ALTER COLUMN PAR_ID SET DEFAULT PAR_SEQ.NEXTVAL ;
  

create table EXCLUSIONS (
    EX_PAR_ID number(38) NOT NULL,
    EX_OTHER_PAR_ID number(38) NOT NULL
);

ALTER TABLE EXCLUSIONS ADD CONSTRAINT EX_PK PRIMARY KEY ( EX_PAR_ID, EX_OTHER_PAR_ID ) ;

ALTER TABLE EXCLUSIONS ADD CONSTRAINT EX_PAR_FK FOREIGN KEY (EX_PAR_ID) REFERENCES PARTICIPANTS (PAR_ID);
ALTER TABLE EXCLUSIONS ADD CONSTRAINT EX_OTHER_PAR_FK FOREIGN KEY (EX_OTHER_PAR_ID) REFERENCES PARTICIPANTS (PAR_ID);


create table WICHTEL_SESSIONS (
    WS_ID number(38) NOT NULL,
    WS_NAME varchar2(255 char) NOT NULL
);

ALTER TABLE WICHTEL_SESSIONS ADD CONSTRAINT WS_PK PRIMARY KEY ( WS_ID ) ;
create sequence WS_SEQ start with 1 nocache;

ALTER TABLE WICHTEL_SESSIONS
  ALTER COLUMN WS_ID SET DEFAULT WS_SEQ.NEXTVAL ;

create table WICHTEL_SESSION_PAIRS (
    WSP_ID number(38) NOT NULL,
    WSP_WS_ID number(38) NOT NULL,
    WSP_PAR_ID number(38) NOT NULL,
    WSP_OTHER_PAR_ID number(38) NOT NULL
);

ALTER TABLE WICHTEL_SESSION_PAIRS ADD CONSTRAINT WSP_PK PRIMARY KEY ( WSP_ID ) ;
create sequence WSP_SEQ start with 1 nocache;

ALTER TABLE WICHTEL_SESSION_PAIRS
  ALTER COLUMN WSP_ID SET DEFAULT WSP_SEQ.NEXTVAL ;
  
ALTER TABLE WICHTEL_SESSION_PAIRS ADD CONSTRAINT WSP_WS_FK FOREIGN KEY (WSP_WS_ID) REFERENCES WICHTEL_SESSIONS (WS_ID);
ALTER TABLE WICHTEL_SESSION_PAIRS ADD CONSTRAINT WSP_PAR_FK FOREIGN KEY (WSP_PAR_ID) REFERENCES PARTICIPANTS (PAR_ID);
ALTER TABLE WICHTEL_SESSION_PAIRS ADD CONSTRAINT WSP_OTHER_PAR_FK FOREIGN KEY (WSP_OTHER_PAR_ID) REFERENCES PARTICIPANTS (PAR_ID);