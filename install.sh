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
echo -ne "Copying COOJA files"
echo -e  "==================="
cp -R $THIS/src/* $DEST/
cp $THIS/start.sh $DEST

# Compiling tunslip
echo -ne "Tunslip6 compilation"
echo -e  "===================="
cd $DEST/tools/
gcc tunslip6.c -o tunslip6
cd $THIS

# Copy Simulation file
echo -ne "Copying simulation files"
echo -e  "========================"
mkdir $DEST/../simulations
cp -r $THIS/simulations/* $DEST/../simulations/


echo "Done."
