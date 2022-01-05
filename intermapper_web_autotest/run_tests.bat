call taskkill /F /IM chromedriver.exe >nul 2>&1

echo "Starting InterMapper Primary Run"

::  ############################ InterMapper regression primary run #######################
call mvn exec:java -D"exec.mainClass"="com.helpsystems.common.util.ClearLastRunResults"
call mvn exec:java -D"exec.mainClass"="com.helpsystems.common.util.ChangeProperty" -Dexec.args="test.properties regression.run.type 'InterMapper Primary Run'"

call mvn surefire-report:report -Dtest=com.helpsystems.intermapperweb.uitest.maplist.Test* -DforkCnt=2
call mvn surefire-report:report -Dtest=com.helpsystems.intermapperweb.uitest.devicelist.Test* -DforkCnt=2
call mvn surefire-report:report -Dtest=com.helpsystems.intermapperweb.uitest.statistics.Test* -DforkCnt=2
call mvn surefire-report:report -Dtest=com.helpsystems.intermapperweb.uitest.help.Test* -DforkCnt=2
call mvn surefire-report:report -Dtest=com.helpsystems.intermapperweb.uitest.errors.Test* -DforkCnt=2
call mvn surefire-report:report -Dtest=com.helpsystems.intermapperweb.uitest.full.Test* -DforkCnt=2
call mvn surefire-report:report -Dtest=com.helpsystems.intermapperweb.uitest.outages.Test* -DforkCnt=2
call mvn surefire-report:report -Dtest=com.helpsystems.intermapperweb.uitest.logout.Test* -DforkCnt=2
call mvn surefire-report:report -Dtest=com.helpsystems.intermapperweb.uitest.login.Test* -DforkCnt=2

::  ############################ InterMapper regression rerun #######################
call mvn exec:java -D"exec.mainClass"="com.helpsystems.common.util.ChangeProperty" -Dexec.args="test.properties regression.run.type 'InterMapper Rerun'"
call mvn exec:java -D"exec.mainClass"="com.helpsystems.common.util.GetFailedTests"
echo "Starting InterMapper Rerun"
call rerun.bat

call mvn exec:java -D"exec.mainClass"="com.helpsystems.common.util.ChangeProperty" -Dexec.args="test.properties regression.run.type 'trash'"
::  ############################ Generate email #######################
call mvn exec:java -D"exec.mainClass"="com.helpsystems.common.util.GenerateEmail"