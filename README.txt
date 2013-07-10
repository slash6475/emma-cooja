********************************
********** EMMA COOJA **********
********************************

/patch: Patches folder
/src/emma-node-example: EMMA example simulation

/install.sh: EMMA COOJA installer

********************************

This provides a patch for Contiki-COOJA simulator. The patch focuses on the Avrora simulator
and specifically on RAVEN targets.

The main features it adds are:
	- Ability to choose the serial port used to perform different tasks


- The patch adds the possibility to choose the serial port that is to be use when running a Serial Socket Server in Cooja (mostly for SLIP connections)

- The patch also enables the use of USART0 and USART1 for RAVEN motes only.

********************************

How to set up a quick simulation ?
