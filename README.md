게시판 리스트 생성용 프로시저

CREATE PROCEDURE testDataInsert()
BEGIN
	DECLARE I INT DEFAULT 1;
    
    WHILE i <= 120 DO
		INSERT INTO BOARD(TITLE, CONTENT)
			VALUES(CONCAT('제목',i),CONCAT('내용',i));
		SET i = i + 1;
	END WHILE;
END$$
DELIMITER$$

CALL testDataInsert;
