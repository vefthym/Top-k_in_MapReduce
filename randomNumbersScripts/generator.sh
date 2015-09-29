for ((i=0; i<$1;++i)) #for as many times, as the first argument ($1) defines...
do 
echo "$i $((RANDOM%$2))" #print the current iteration number and a random number in [0, $2)
done
