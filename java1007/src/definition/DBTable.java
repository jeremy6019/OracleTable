package definition;

public class DBTable {
/*
 
**   DDL(data Definition Language - 데이터 정의 언어)
=>데이터 베이스  개체(테이블, 뷰, 인덱스,프로시저,트리거등)를 생성하고 구조를 변경하고 
삭제하는 명령어 

1. 자료형 
=>데이터베이스 내의 컬럼에 저장할 수 있는 데이터의 타입 
1)숫자
- number : 서수점이나 부호를 제외하고 38자리 까지 저장 가능 
- number(정수) : 최대 정수 자리만큼 저장 가능 
- number(전체길이, 소수길이): 전체길이를 확보해서 소수만큼을 소수로 사용 
- 자바에서의 숫자 자료형과 매핑시킬 수 있고 자료형을 설정하지 않은 경우 
   BigDecimal과 매핑되면 String 과도 매핑가능 

2) 문자 
- char(자리수): 자리수 만큼 공간을 확보해서 저장하고 공간의 크기가 변하지 않음 

- varchar2(자리수): 자리수 만큼 공간을 확보해서 저장하고 공간보다 작은 개수의 문자를
저장하면 공간의 크기가 줄어듭니다. 

- clob : 긴 문자열을 저장할 때 사용하는 자료형인데 인덱스(빠르게 검색하기 위한 포인터) 
설정을 못합니다. 

=> 영문 1000자 이상은 clob을 사용 
=> 한글은 utf-8에서 3byte를 차지합니다.
=> char는 공간을 줄이지 않기 때문에 문자열 뒤에 공백이 있을 수 있습니다.
프로그램에서 읽어갈 때 주의 해야 합니다.    
=> varchar2는 공간의 크기가 변하기 때문에 실제로는 주의해야 합니다. 
데이터가 변경이 된다면 공간의 크기가 다시 변경될 수 있습니다. 
이 때 잘못하면 row migration(행이주)이 발생해서 데이터의 수정이 일어날 때 
아주 많은 시간을 소비할 수 있습니다. 
프로그래밍 언어 교재들은 varchar2를 많이 사용하는데 위험할 수 있습니다. 
=> 프로그래밍 언어(java)와 연결할 때는 String

3) 날짜 
Date: 밀리초까지 저
Timestamp: 나노초까지 저장 
=> 날짜는 출력만 하고자 할 때는 문자로 저장을 많이 하고 연산을 할 때는 Date로 저장
하는 경우가 많습니다. 
=> 데이터베이스가 날짜 연산은 쉽게하기 때문입니다. 
=>자바와 연결할 때는 java.util.Date(초단위까지 저장)나  java.sql.Date(날짜만 저장)
그리고 String

4)BLOB 
=>파일의 내용을 저장 
=>파일 자체를 저장하고자 할 때  사용하는 자료형인데 자바와 연결할 때는 바이트배열로
연결합니다.
=>파일의 내용을 저장할 때 파일을 만들고 이름을 저장하는 경우가 있고 파일의 내용을 
저장하는 경우도 있습니다. 
파일의 이름만 저장한 경우에는 파일의 경로 이동이 쉽지 않습니다. 

2.테이블 생성 구문 
create table 테이블 이름(
    컬럼이름 자료형 제약조건, 
    ... 
    테이블 제약조건);
=> SQL기본 예약어와 테이블이름, 컬럼이름은 대소문자 구분을 하지 않습니다.
컬럼안에 삽입되는 데이터만 대소문자 구분을 합니다.
예약어와 다른 데이터들은 중간에 공백이 있어야 합니다. 

** 데이터베이스 연결 
1. 데이터베이스 접속 프로그램 필요 - DBeaver

2.접속에 필요한 정보 
1)접속할 컴퓨터의 ip나 이름 
192.168.0.13 - 자기 컴퓨터는 localhost 

2) 접속할 포트번호 
1521 - 오라클의 기본 접속포트는 1521 

3)접속할 데이터베이스 이름이나 서비스 이름 
xe - 	기본이름은 orcl(Enterprise Edition), xe(Express Edition)
오라클 18c부터는 이름이 아니고 서비스 이름입니다. 

 4)접속할 계정과 비밀번호 
 user05 - user05 
 - 직접 설치한 로컬의 경우는 sys와 system계정이 있고 설치할 때 비밀번호를 사용 
 
3. 접속도구에 따라 데이터베이스 연결 드라이버가 필요한 경우가 있음 
 => 이 경우는 접속도구가 하나의 데이터베이스에만 사용 가능하지 않고 여러종류의 
 데이터베이스에서 사용이 가능한 경우 
 =>오라클을 사용할 때는 	ojdbc.jar파일이 필요(오라클 홈페이지 또는 오라클이 설치된 
 경우에는 오라클의 jdbc/lib디렉토리에 있음) 

** 테이블 생성 
- 회원 정보를 저장하는 테이블: SQLite가 보통 제약조건 없이 많이 만듬 
email-변경될 가능성이 없는 문자열 30자리 
password - 자주 변경되는 문자열 15자리 
name -변경될 가능성이 없는 문자열 15자리 
alias -변경될 가능성이 높은 문자열 15자리 
birthday -날짜 
age -정수 3자리 

create table member(
email varchar2(30), 
password char(15),
name varchar2(15), 
alias char(15), 
birthday date,
age number(3)); 

- 테이블  생성여부 확인 
테이블 구조확인 
=> desc 테이블 이름; 이명령은 접속도구에 따라서 실행이 안되기도 함 

2) 데이터 확인 
select * 
from 테이블 이름 

3)GUI접속도구들은 navigator나 explorer창에서 확인 가능 

-- 서브쿼리를 이용해서 테이블 만들기 
기존테이블의 데이터나 구조를 복사해서 만들기 
create table 테이블 이름 
as 
select 구문 

-- emp테이블의 모든 데이터를복사해서 empcopy라는 테이블 생성 
create table empcopy 
as 
select * 
from emp;

확인 
select * 
from empcopy;

 -14행 8열로 나오면 맞게 수행 

-- emp테이블에서 deptno 가 30인 데이터의 empno, ename, sal, deptno컬럼만 
-- 가지고 emp30이라는 테이블을 생성 
CREATE TABLE EMP30
AS 
SELECT EMPNO, ENAME, SAL, DEPTNO 
FROM EMP 
WHERE DEPTNO = 30;

-- 확인 : 6개의 행 
SELECT * 
FROM EMP30;

--emp테이블의 구조만 복사해서 emptemp라는 디렉토리를 생성 
where절에 무조건 false가 되는 조건을 설정 
CREATE TABLE EMPTEMP
AS 
SELECT *
FROM EMP 
WHERE 0 = 1;

-- 확인 : 데이터가 1개도 안나오면 됩니다. 
SELECT * 
FROM EMPTEMP;
  
** 테이블의 구조 변경 - Alter 
1.컬럼 추가 
alter table 테이블 이름 add(컬럼이름 자료형 제약조건);

2.컬럼 수정 
alter table 테이블 이름 modify(컬럼이름 자료형 제약조건);
=>자료형을 변경하는 경우가 많은데 자릿수를 늘리는 것은 아무런 문제가 없지만 
자리수를 줄이는 것은 데이터를 확인하고 하는 것이 좋습니다. 
데이터가 잘릴 수 있기 때문입니다. 

3. 컬럼 삭제 
alter table 테이블 이름 drop column 컬럼이름;
=> 삭제는 안되는 경우가 있는데 이경우는 이 컬럼이 다른 테이블의 참조키(외래키)인 경우

- member테이블에 mobile은 무조건 11자리이고 데이터가 자주 변경되는데 이컬럼을 
추가 
alter table member add(mobile varchar2(11));

-확인은 
SELECT * 
FROM MEMBER;
=> mobile컬럼이 생겼는데 확인 

** 테이블 삭제 
drop table 테이블이름; 
=>테이블이 삭제가 안될 수 있는데 이때는 다른 테이블의 참조가 되는 컬럼이 있는 경우 
삭제가 안될 수 있습니다. 

-- emp30테이블 삭제 
 drop table emp30; 
 
 --확인 
 select * 
 from emp30; 
 =>테이블이나 뷰가 없다고 에러메시지 출력 
 
** 테이블의 데이터만 삭제 - 표준은 아니어서 안되는 데이터베이스도 있음 
truncate table 테이블이름;

** 테이블 이름 변경 
rename 기존테이블이름 to 변경할 테이블 이름;

** 개발자한테 중요한 SQL순서 
select -> insert, delete, update -> commit, rollback -> create, alter, drop ->
grant, revoke(권한 부여 회수)

** DML(데이터 조작 언어) 
=> 테이블에 데이터를 추가하고 삭제하고 수정하는 명령 
=> insert, delete, update 

1.insert 
1)컬럼이름을 나열해서 삽입 
insert into 테이블이름(컬럼이름나열) 
values (데이터를 나열);
=> 문자를 입력할 때는 ' ' 사이에 작성 
=> 날짜를 입력할 때는 sysdate는 현재날짜및 시간이고 to_date('날짜 형식의 문자열', 
'날짜 서식') 

=> 샘플 테이블 생성 
게시판 테이블 - Board 
글번호 - 정수 5자리 
글제목 - 한글을 포함한 30자리까지 : 변경 가능성이 거의 없음 
글내용 - 1000자 이상의 문자열 
작성한 곳의 IP주소 - 문자열로 한글이 없고 40자리까지 
작성자이메일 - 변경 가능성이 없는 영문과 숫자 30자리까지 
작성일 - 날짜 

drop table board; - 테이블 삭제 

create table board(
  num number(5),
  title varchar2(90),
  content clob,
  ip varchar2(40),
  email varchar2(30),
  regdate date);
  
--데이터 삽입 
num=1, title =제목, content=내용, ip =127.0.0.1
email=jeremy94@namer.com, regdate는 현재시간으로 데이터를 삽입 

insert into board(num,title,content,ip,email,regdate)
values(1,'제목','내용','127.0.0.1',
'jeremy94@naver.com',sysdate);

num=2, title=가입인사,content=안녕하세요 반갑습니다.
email=jeremy6019@daum.net, regdate는 2019년 10월6일 정오로 삽입 

INSERT INTO board(num,title,content,email,regdate)
values(2,'가입인사','안녕하세요 반갑습니다','jeremy6019@daum.net',
to_date('2019-10-06 12:00:00','yyyy-mm-dd hh:mi:ss'));

3.데이터 확인 
select*
from board; 

=> 컬럼에 값을 입력하지 않은 상태에서 삽입을 하면 NULL이나 기본값으로 설정 
=> NULL을 삽입하고자 할 때는 직접 NULL을 설정해도 되고 기본값이 없는 경우에는
컬럼을 생략한 채로 삽입하면 됩니다. 
 
2)컬럼이름을 생략하고 삽입 
=>컬럼이름을 생략한 경우에는 테이블을 만들때 사용한 컬럼의 순서대로 모든 데이터를 
직접 입력해야 합니다. 

insert into 테이블이름 
values(값을 나열);

=>테이블을 만들때 사용한 구문이 있다면 순서대로 삽입이 가능하지만 테이블을 만들때 
사용한 구문은 없고 명세(구조)만 가지고 있는 경우는 제대로 삽입이 안될 수 있으므로 주의 

3.num=3, title =컬럼이름없음, content=구조를 모르면 사용하지 않음, ip =127.0.0.1
email=jeremy94@namer.com, regdate는 현재시간으로 데이터를 삽입 

insert into board
values(3,'컬럼이름없음','구조를 모르면 사용하지 않음','127.0.0.1',
'jerome94@daum.net',sysdate);

127.0.0.1과 0::0::0::0::0::0::0::1은 자신의 루프백 주소 
자신의 컴퓨터에서 접속해서 ip를 조사하면 위처럼 나오게 됩니다.
127.0.0.1은 IPv4인데 win7이하에서 사용하고 win8부터는 IPv6인 
0::0::0::0::0::0::0::1 주소를 사용합니다.

3)서브쿼리를 이용해서 데이터 삽입 
insert into 테이블이름(컬럼이름 나열)
as 
select 구문 

=>주의할 점은 나열한 컬럼이름이 select절의 컬럼이름과 같을 필요는 없지만 
자료형과 개수는 같아야 합니다,. 
=> 데이터베이스에서 join을 한다던가 foreign key를 만든다던가 할 때 컬럼이름은 
같을 필요가 없고 자료형이 같아야 합니다. 

dept테이블과 구조가 동일한 deptcopy테이블을 만들고 dept테이블의 데이터를 
insert구문을 이용해서 deptcopy테이블로 복사 

-- 테이블구조 복사 
create table deptcopy 
as 
select * 
from dept 
where 0=1; 

- 데이터 추가 
insert into 테이블이름(컬럼이름)
values (값을 나열)

insert into deptcopy(deptno, dname, loc)
select deptno, dname, loc
from dept;   

-확인 
select * 
from deptcopy;
=>데이터가 4개인지 확인 

연습문제 
--emp테이블의 구조를 복사해서 empcopy를 생성 
기존의 empcopy가 있다면 테이블을 삭제하고 생성 

DROP TABLE empcopy;

CREATE TABLE empcopy
AS 
SELECT *
FROM emp 
WHERE 0 = 1;

--서브쿼리를 이용한 insert구문을 이용해서 deptno가 20인
 데이터만 empcopy테이블에 삽입 

INSERT INTO empcopy
SELECT *
FROM EMP 
WHERE deptno = 20;

SELECT*
FROM empcopy;

2.데이터 수정 : UPDATE 
UPDATE 테이블 이름 
SET 수정할 내용 나열(컬럼이름 = 연산식이나 값, 컬럼이름 = 연산식이나 값...)
WHERE 조건;
=>WHERE는 생략이 가능한데 생략하면 테이블의 모든 데이터가 수정됩니다. 

-- empcopy테이블에 deptno를 전부 30으로 수정 
update empcopy 
set deptno = 30;

SELECT*
FROM empcopy;
명령을 수행해서 deptno가 30으로 변경되었는지 확인 

-- empcopy테이블에서 sal이 1000보다 작은 사원의 sal을 10% 인상 
update empcopy 
set sal = sal * 1.1 
where sal < 1000; 

=> 전체 데이터를 확인하면 smith의 sal이 880으로 수정 

-- emcopy테이블에서 ename이 S로 끝나는 데이터의 deptno를 20으로 sal
은 10% 인상하고 comm을 3000으로 수정 
UPDATE empcopy 
SET deptno = 20, sal = sal * 1.1, comm = 3000
WHERE ename LIKE '%S';

=>확인은 전체 조회를 해서 JONES와 ADAMS데이터의 COMM 과 DEPTNO를
확인 

3.데이터 삭제 
delete from 테이블 이름 
where 조건; 
=> 조건이 생략되면 테이블의 모든 데이터가 삭제됩니다. 

 --empcopy테이블에서ename의 3번째 글자가 A인 데이터를 삭제 
 delete from empcopy 
 where ename like'__A%'; 
 
 => 데이터를 확인하면 4개남고 ename이  ADAMS인 데이터만 삭제 
 
** 트랜잭션 제어어(Transaction Control Language)
1.트랜잭션 
1) 정의 
=>한번에 이루어져야하는 작업의 논리적인 단위   

2)특성 
Atomicity(원자성): 트랜잭션은 All or nothing - 전부 수행되거나 하나도 수행되지
않아야 한다 

Consistency(일관성): 트랜잭션 수행 전과 수행 후가 일관성이 있어애 한다 

Isolation(격리성): 하나의 트랜잭션은 다른 트랜잭션과 격리되어서 수행되어야 한다. 

Durability(영속성, 지속성): 한 번 완료된 트랜잭션은 계속되어야 한다. 

2. Oracle에서의 트랜잭션 명령어 
1)commit: 현재까지의 작업 내용을 원본에 반영 

2)savepoint 이름: rollback할 지점의 이름을 만드는 것 

3)rollback to 저장점이름: 저장점이름을 생략하면 트랜잭션 시작점으로 이동하고 
저장점이름을 작성하면 저장점이 만들어지는 시점으로 이동 

3. 트랜잭션 사용방법 
1)auto commit: 하나의 sql문장을 성공정으로 수행하면 commit
=>자바는 기본이 auto commit 

2)manual commit: 직접 commit이나 rollback을 수행하는 것 
=>오라클은 기본이 manual commit 

4. 트랜잭션 작업 
1)DDL(Create, Alter, Drop, Truncate, Rename)은 성공적으로 수행되면 commit됩니다.

2)commit된 상태에서 DML(insert, update, delete)를 성공적으로 수행하면 새로운 트랜잭션이
만들어 집니다. 

3)정상적으로 접속 프로그램을 종료하면 commit됩니다. 

4)비정상적으로 접속 프로그램이 종료되면 rollback됩니다. 

5.DBever는 기본적으로 auto-commit

** 트렌잭션 연습 
1.DEPT테이블의 모든 데이터를 복사해서 DEPTTCL테이블을 생성 
CREATE TABLE DEPTTCL 
AS 
SELECT * 
FROM DEPT;

2.테이블 확인 
SELECT * 
FROM DEPT;
=> DEPTNO가 10,20,30,40	인 데이터가 4개가 존재 
컬럼은 DEPTNO, DNAME, LOC 3개가 존재 

3.DEPTNO가 30인 데이터를 삭제 
DELETE FROM DEPTTCL 
WHERE DEPTNO = 30;

4. 데이터 확인 
SELECT * 
FROM DEPTTCL ;
=> 30인 데이터가 삭제되서 3개의 행만 존재 

5.트랜잭션의 작업을 취소 
ROLLBACK;

6. 데이터 확
SELECT * 
FROM DEPTTCL;
=>insert, delete, update작업은 commit을 하기전에 rollback을 하면 취소가 되어야 하는데 
현재 데이터는 3개 
=> 현재 트랜잭션 사용방법이 AutoCommit으로 되어 있기때문에 어떠한 SQL이 더라도 
성공적으로 수행되면 바로 Commit되어 버립니다. 

7.DBever의 트랜잭션 사용옵션을 manual로 변경 
=>Auto대신에 None이 보임 

8.DEPTTCL테이블에서 DEPTNO가 40인 데이터를 삭제 
DELETE FROM DEPTTCL 
WHERE DEPTNO = 40;
=> 데이터가 삭제되서 2개의 행이 출력 
=> Manual Commit이라서 명령이 성공적으로 수행되더라도 Commit되지 않음 

9. 데이터 확인 
SELECT * 
FROM DEPTTCL;

10. 작업 취소 
ROLLBACK;

11. 데이터 확인 
SELECT * 
FROM DEPTTCL;

12. DEPTTCL테이블에 DEPTNO가 50이고 DNAME 은 ACCOUNTING 이고 LOC는 LA
인 데이터를 삽입 - INSERT(DML)수

INSERT INTO DEPTTCL(DEPTNO, DNAME, LOC) 
VALUES(50,'ACCOUNTING','LA');

13. DEPTTCL테이블에서 DEPTNO가 40인 데이터의 LOC를 LA로 수정 
-UPDATE(DML)수행 
UPDATE DEPTTCL 
SET LOC = 'LA'
WHERE DEPTNO=40;

14. ROLLBACK을 수행 
=>12번 작업까지 전부 취소 
ROLLBACK;

15. 어디까지 취소
SELECT * 
FROM DEPTTCL;
12,13번 취소  

16. DEPTTCL테이블에 DEPTNO가 60이고 DNAME이 비서이고 LOC가 SEOUL인 
데이터를 삽입  - INSERT(DML)수
INSERT INTO DEPTTCL(DEPTNO,DNAME, LOC)
VALUES(60,'비서','SEOUL');

17. DEPTTCL테이블에서 LOC컬럼을 제거 
-ALTER(DDL)수행 : COmmit이 됩니다. 
ALTER TABLE DEPTTCL DROP COLUMN LOC;

18.DEPTTCL테이블에 DEPTNO가 70이고 DNAME이 총무인 데이터를 삽입 
-INSERT(DML) 수행 
INSERT INTO DEPTTCL(DEPTNO,DNAME)
VALUES(70,'총무');

19.ROLLBACK 을 수행 
ROLLBACK; 
=> 17번 작업만 취소 

20.DEPTTCL테이블에서 모든 데이터를 삭제 
DELETE FROM DEPTTCL; 
=>FROM을 생략해도 됨 
=>TRUNCATE TABLE DEPTTCL하게되면 모든 데이터가 삭제되는데 DDL이라서
바로 COMMIT됩니다. 
=>DELETE에서 WHERE절을 생략한 것은 DML이라서 COMMIT을 호출하거나 
DDL문장을 수행하지 않았다면 취소가 가능합니다. 

21. 현재까지의 작업내용을 원본에 반영 
COMMIT; 

22.ROLLBACK을 수행해도 작업취소가 안됨 

** COMMIT 과 ROLLBACK만으로 트랜잭션을 하게되면 ROLLBACK을 할 때 너무
많은 작업이 취소될 수 있습니다.
작업을 하는 중간 중간에 SAVEPOINT를 추가해서 ROLLBACK지점을 설정할 수 
있습니다. 
ROLLBACK을 할 때 SAVEPOINT 이름을 같이 지정하면 그 지점까지 작업을 
취소시킬수 있습니다. 
한 번 취소되어 버린 작업은 되살릴 수 없습니다. 

작업1 
SAVEPOINT S1
작업2 
SAVEPOINT S2 
작업3 
SAVEPOINT S3 

ROLLBACK TO S2
이제 작업3은 복구할 수 없습니다. 
실습을 할 때 DBever는 상관없는데 SQL접속도구 중에서 SAVEPOINT를 
1개만 만들 수 있도록 하는 경우도 있습니다. 

23.DEPTTCL테이블에 DEPTNO가 11번이고 DNAME 이 총무인 데이터를 추가 
INSERT INTO DEPTTCL(DEPTNO,DNAME)
VALUES(11,'총무');

24. s1이라는 savepoint 추가 
SAVEPOINT S1;

25.DEPTTCl테이블에 DEPTNO가 22번이고 DNAME 이 영업인 데이터를 추가 
INSERT INTO DEPTTCL(DEPTNO,DNAME)
VALUES(22,'영업');

26.s2이라는 SAVEPOINT 추가 
SAVEPOINT S2;

27. S2로 롤백 
ROLLBACK TO S1;

28. 데이터 확인 :24번 자리로 롤백되기 때문에 11번만 데이터만 추가되어 있음 
SELECT * 
FROM DEPTTCL;

(무결성(Integrity))제약조건(Constraint)
=>테이블에서 반드시 지켜야할 조건 
1. NOT NULL: NULL일 수 없다. 
2. UNIQUE: 중복된 값을 가질 수 없다. 
3. PRIMARITY(PK, 기본키,주키):  NOT NULL이고 UNIQUE
    테이블에서 1개만 지정 가능, 2개 컬럼이상으로 만들어도 됨 
4. CHECK : 값의 범위를 설정 
5. FOREIGN Key(참조키, 외래키)
=> 다른 테이블의 데이터를 참조하기 위한 속성 
=> 이론에서는 다른 테이블에서 PK이어야 한다. 
=> 실제 DB에서는 UNIQUE한 속성도 가능 
6. DEFAULT: 기본값 설정 
DEFAULT SYSDATE - 기본값이 오늘 날짜 

* 개체 무결성 
=>기본키는 NULL이거나 중복될 수 없다. 
* 참조 무결성 
=>외래키는 NULL이거나 참조할 수 있는 값만 가져야 한다. 










 
 
 



  
  
  

 */
}
