# -agentlib:jdwp=transport=dt_socket,server=y,address=9090,suspend=n

web: java
-Dpayu.pos_id=$PU_POS_ID
-Dpayu.second_key=$PU_SECOND_KEY
-Dpayu.client_id=$PU_CLIENT_ID
-Dpayu.client_secret=$PU_CLIENT_SECRET
-Dspring.datasource.url=$DBADDR
-Dspring.datasource.username=$DBUSR
-Dspring.datasource.password=$DBPW
-Dspring.mail.username=$MAILPW
-Dspring.mail.password=$MAILUSR
-Dserver.port=$PORT
-jar target/bussystem-0.0.1-SNAPSHOT.jar