
-- guestbook_log
update guestbook_log set count = count+1 where date = current_date();
insert into guestbook_log values(current_date(),1);

update guestbook_log set count=count-1 where date = (select date(reg_date) from guestbook where no = 30);

SELECT * FROM guestbook_log;
SELECT * FROM guestbook;
delete from guestbook_log;