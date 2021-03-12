# Avier
Avier is a program for reading payments and stamping them on to a pdf form (Intended for paying rent).
The pdf template used is the Swedish plusgiro and bankgiro form.

### Config
avier.conf includes general information for the forms. There are th properties:
name, plusgiro and bankgiro. The name should be the name of the payment receiver. Plusgiro and bankgiro
should be the payment receivers accounts.

### Adding payments
avier.txt includes the payment information. Lines beginning with '#' will be ignored.
The syntax is very simple, each line will be parsed as Type Name Payment. Type will be the
first word parsed and payment the last. To add a payment, just add a new line.

### Generating pdf to print
After you configured and added payements its time to generate the pdf.
Using Maven, run mvn clean package in the folder and it will generate a .jar file.
Run the jar file to start the GUI. In the GUI (disclaimer: it's really horrible),
press the "create pdf" button. This generates a .pdf file in the folder. Print, cut the
the dotted lines and send to your payers.
