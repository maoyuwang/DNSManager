javac -cp ".;../lib/*" -encoding utf-8 ../src/*.java -d ../out
cd ../out && java -cp ".;../lib/*" App
