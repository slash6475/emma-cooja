#!/bin/bash

THIS=$( cd "$( dirname "$0" )" && pwd )

usage()
{
        echo "emma-cooja install"
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
#patch -p2 --directory=$DEST --input=$THIS/patch/cooja_avrora-serial-socket_patch.diff

# Copy COOJA File
cp -R $THIS/src/* $DEST/

# Copy Simulation file
mkdir $DEST/../simulations
cp $THIS/simulations/* $DEST/../simulations/
cp $THIS/start.sh $DEST
echo "Done."
