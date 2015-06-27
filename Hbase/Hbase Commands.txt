Creating tables in Hbase:
> hbase shell

To get the list of all the table.
>list
>whoami
>status

creating table.
---------------
create 'user', ['FirstName','LastName','EmailId','City']
describe 'user'

Inserting data.
---------------
put 'user', 'Revanth', 'FirstName:first_name', 'Revanth'
put 'user','Revanth', 'LastName:last_name', 'Reddy'
put 'user','Revanth','City:city', 'Bangalore'
put 'user','Revanth','EmailId:emailId', 're1t.sr@gmail.com'
put 'user', 'Lokesh', 'FirstName:first_name', 'Lokesh'
put 'user','Lokesh', 'LastName:last_name', 'LokeshBusa'
put 'user','Lokesh','City:city', 'Bangalore'
put 'user','Lokesh','EmailId:emailId', 'lokeshbusa@gmail.com'

>count 'user' (To get the total rows in table)

For getting data from the table.
----------------------------------
>get 'user', 'Lokesh'
>scan 'user'

For deleting data from the table.
---------------------------------
>delete 'user', 'Lokesh', 'FirstName:first_name'

For deleting the table.
>disable 'user'
>drop 'user'

To add new column to the table.
----------------------------------
disable 'user' 
alter 'user', NAME => 'Profession' 
put 'user', 'Lokesh', 'Profession:Profession', 'SE'
get 'user', 'Lokesh'

To delete new column to the table .
------------------------------------
disable 'user'
alter 'user', 'delete' => 'Profession'

To delete all/single columns in a given row.
--------------------------------------------
deleteall 'user', 'Re1t'
deleteall 'user', 'Lokesh','City:city'

To create a reference to table.
------------------------------- 
t =  get_table 'user'
t.scan
t.deleteall 'Revanth','City:city'
t.get 'Madhu','City:city'

Version's in Hbase (By default it has 3 versions).
--------------------------------------------------
put 'user','Lokesh','City:city', 'Hyd'
put 'user','Lokesh','City:city', 'BLR'
get 'user', 'Lokesh', {COLUMN => 'City:city', VERSIONS => 3}

disable 'user'
put 'user','Lokesh','City:city', 'AP'
put 'user','Lokesh','City:city', 'TN'
alter 'user', {NAME => 'City', VERSIONS => 5}

truncate - Disables, drops, and recreates a specified table
-----------------------------------------------------------
truncate 'user'