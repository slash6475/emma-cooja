#!/bin/bash

THIS=$( cd "$( dirname "$0" )" && pwd )

usage()
{
	echo "install.sh DESTINATION"
	echo "		DESTINATION: The location of a Contiki directory (David Kopf branch)"
}

if [ ! -d "$1" ]; then
	usage
	exit
else
	DEST="$1"
fi

echo "Installing 'emma-cooja' ..."

# Patch Contiki OS
patch -p2 --directory=$DEST --input=$THIS/patch/cooja_avrora-serial-socket_patch.diff

# Copy Simulation file
mkdir $DEST/../Simulations
cp $THIS/src/emma-node-example.csc $DEST/../Simulations

echo "Done."
