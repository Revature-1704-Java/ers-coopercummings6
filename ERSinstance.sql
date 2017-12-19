BEGIN   --oracle didn't bother to implement drop table if exists
    EXECUTE IMMEDIATE 'DROP TABLE Employee';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/
CREATE TABLE Employee
(
    EmployeeId NUMBER NOT NULL,
    EmployeeName VARCHAR2(160) NOT NULL,
    ManagerId NUMBER NOT NULL,  --managerID should not be null, because a manager needs to approve all requests, so a manager may need to be their own manager
                                --for this purpose, but it should not be null
    Password VARCHAR2(5),
    CONSTRAINT PK_EmployeeID PRIMARY KEY (EmployeeID)
);

BEGIN   --oracle didn't bother to implement drop table if exists
    EXECUTE IMMEDIATE 'DROP TABLE Reimbursement';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/
CREATE TABLE Reimbursement
(
    ReimbursementId NUMBER NOT NULL,
    Description VARCHAR2(700) NOT NULL,
    Amount NUMBER NOT NULL,
    Approved CHAR check (Approved in (0,1)),        --oracle didn't feel like supporting boolean data types
    Completed CHAR check (Completed in (0,1)),
    CONSTRAINT PK_ReimbursementID PRIMARY KEY  (ReimbursementId)
);

CREATE SEQUENCE Reimbursement_keys
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

BEGIN   --oracle didn't bother to implement drop table if exists
    EXECUTE IMMEDIATE 'DROP TABLE EmployeeReimbursements';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/
CREATE TABLE EmployeeReimbursements
(
    EmployeeId NUMBER NOT NULL,
    ReimbursementId NUMBER NOT NULL,
    CONSTRAINT PK_EmployeeReimbursements PRIMARY KEY  (EmployeeId, ReimbursementId)
);

BEGIN   --oracle didn't bother to implement drop if exists
    EXECUTE IMMEDIATE 'DROP CONSTRAINT FK_EmployeeId';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/
ALTER TABLE EmployeeReimbursements ADD CONSTRAINT FK_EmployeeId
    FOREIGN KEY (EmployeeId) REFERENCES Employee (EmployeeId)  ;
    

BEGIN   --oracle didn't bother to implement drop if exists
    EXECUTE IMMEDIATE 'DROP CONSTRAINT FK_ReimbursementId';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/
ALTER TABLE EmployeeReimbursements ADD CONSTRAINT FK_ReimbursementId
    FOREIGN KEY (ReimbursementId) REFERENCES Reimbursement (ReimbursementId)  ;

BEGIN   --oracle didn't bother to implement drop if exists
    EXECUTE IMMEDIATE 'DROP CONSTRAINT FK_ManagerID';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/
ALTER TABLE Employee ADD CONSTRAINT FK_ManagerID
    FOREIGN KEY (ManagerId) REFERENCES Employee (EmployeeId)  ;
    
INSERT INTO Employee (EmployeeID, EMPLOYEENAME, ManagerId, Password) VALUES (1, 'Jeff', 1, '1234');
INSERT INTO Employee (EmployeeID, EMPLOYEENAME, ManagerId, Password) VALUES (2, 'James', 1, 'passw');
INSERT INTO Employee (EmployeeID, EMPLOYEENAME, ManagerId, Password) VALUES (3, 'Jake', 1, '12345');
INSERT INTO Employee (EmployeeID, EMPLOYEENAME, ManagerId, Password) VALUES (4, 'Jim', 1, 'NULL');
select * from employee;
COMMIT;

