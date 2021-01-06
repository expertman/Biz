CREATE TABLE Board
(
    idx              NUMBER(4),
    name          VARCHAR2(20)  NOT NULL,
    email           VARCHAR2(100),
    title             VARCHAR2(200) NOT NULL,
    contents      VARCHAR2(2000) NOT NULL,
    writedate      DATE    DEFAULT SYSDATE   NOT NULL,
    readcount     NUMBER(4)   DEFAULT 0   NOT NULL,
    filename       VARCHAR2(200),
    CONSTRAINT board_idx_pk  PRIMARY KEY(idx)
);

CREATE  SEQUENCE board_idx_seq
    START WITH 1
    INCREMENT BY 1
    MAXVALUE    9999
    NOCACHE
    NOCYCLE;

--게시판 목록 가져오기
CREATE OR REPLACE PROCEDURE sp_board_select_all
(
    board_record    OUT   SYS_REFCURSOR
)
AS
BEGIN
    OPEN board_record FOR
    SELECT idx, name, email, title, writedate, readcount, filename
    FROM Board  
    ORDER BY idx DESC;
END;

--새로운 글 입력하기
CREATE OR REPLACE PROCEDURE sp_board_insert
(
    v_name       IN     Board.name%TYPE,
    v_email      IN     Board.email%TYPE,
    v_title      IN     Board.title%TYPE,
    v_contents    IN     Board.contents%TYPE,
    v_filename    IN     Board.filename%TYPE
)
IS
BEGIN
    INSERT INTO Board(idx, name, email, title, contents, filename)
    VALUES(board_idx_seq.NEXTVAL, v_name, v_email, v_title, v_contents, v_filename);
    COMMIT;
END;