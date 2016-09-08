#!/bin/bash
#*******************************************************************************
# Copyright (c) 2016 Dominique Lopes.
# All rights reserved. 
#
# This Source Code Form is subject to the terms of the Mozilla Public 
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/.
#
# Contributors:
#     Dominique Lopes - initial API and implementation
#*******************************************************************************

if [ "$2" = "" ]
then

   echo USAGE:
   echo " push2bluemix.sh -v [packageVersion]"
   echo "   packageVersion - version of current maven artifact to upload (defined in pom.xml)"

else

   path="target/facilitator-$2-SNAPSHOT.war"
   echo "building $path ..."
   
   mvn package -P war
   bluemix api https://api.ng.bluemix.net
   bluemix login -u d.lopes@gmx.de -o "D. Lopes" -s "Stock Facilitator"
   
   echo "uploading $path ..."
   cf push "Stock Facilitator" -p $path
	
fi
