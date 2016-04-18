#Avier
Avier is a program for reading payments, stamping them on to a pdf form and print them.
The pdf template used is the Swedish plusgiro form and is supplied in the project.

###Config
avier.conf includes general information for the forms. Currently, there is only two properties used:
name and account. Name will be the name of the person that are to receive the money and account will be
that persons plusgiro account.

###Adding payments
avier.txt includes the payment informations. Lines beginning with '#' will be ignored.
The syntax is very simple, every line will be parsed as Name Payment, where payment
will be the last word in the line.