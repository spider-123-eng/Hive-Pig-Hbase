REGISTER /usr/lib/pig/piggybank.jar;

DEFINE ApacheCommonLogLoader org.apache.pig.piggybank.storage.apachelog.CommonLogLoader();


DEFINE DayExtractor org.apache.pig.piggybank.evaluation.util.apachelogparser.DateExtractor('yyyy-MM-dd');

--load the log files from hdfs into pig using CommonLogLoader

logs = LOAD '/user/cloudera/Pig/access_log' USING ApacheCommonLogLoader AS (ip_address, rln, user, dt, request, uri, serverstatus, bytes);


--Requirement : Find unique ip per day

--Extracting the day alone and grouping records based on days

grpd = GROUP logs BY DayExtractor(dt); 

--looping through each group to get the unique no of ip_address
cntd = FOREACH grpd
{
                tempId =  logs.ip_address;
                uniqueIpAddress = DISTINCT tempId;
                GENERATE group AS day,COUNT(uniqueIpAddress) AS cnt;
}
--sorting the processed records based on no of unique user ids in descending order
srtd = ORDER cntd BY cnt;
--storing the final result into a hdfs directory
STORE srtd INTO '/user/cloudera/ApacheLogResult';

