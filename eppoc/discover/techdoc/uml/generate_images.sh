#!/bin/ksh
#
# Utility to convert generate images from umlet
#
# $Id: generate_images.sh 11492 2013-12-04 10:30:12Z plord $

umletjar="$PWD/Umlet/umlet.jar"
umlet="java -jar ${umletjar}"

if [ ! -f ${umletjar} ]
then
	wget http://www.umlet.com/umlet_13_3/umlet_13.3.zip
	unzip umlet_13.3.zip
fi

maxwidth=500
maxheight=600

maxwidth_svg=800
maxheight_svg=900

for uxf in *.uxf
do
	#png=$(basename $uxf .uxf).png
	jpeg=$(basename $uxf .uxf).jpeg
	svg=$(basename $uxf .uxf).svg
	#ln -sf ${jpeg} ${svg}.jpg

	#if [ ${uxf} -nt ${png} ]
	#then
	#	echo "${uxf} -> ${png}"
	#	${umlet} -action=convert -format=png -filename=${uxf}
	#fi

	if [ ${uxf} -nt ${svg} ]
	then
		echo "${uxf} -> ${svg}"
		${umlet} -action=convert -format=svg -filename=${uxf}

		width=$(identify -format "%[fx:w]" ${svg})
		height=$(identify -format "%[fx:h]" ${svg})

		let wdiff=${width}-${maxwidth_svg}
		let hdiff=${height}-${maxheight_svg} 

		if [ ${width} -gt ${maxwidth_svg} -a ${wdiff} -gt ${hdiff} ]
		then
			let h=${height}*${maxwidth_svg}/${width}
			sed -i -e "/^<svg/s+width=\"${width}\"+width=\"${maxwidth_svg}\"+" ${svg}
			sed -i -e "/^<svg/s+height=\"${height}\"+height=\"${h}\"+" ${svg}
		fi
		if [ ${height} -gt ${maxheight_svg} -a ${hdiff} -ge ${wdiff} ]
		then
			let w=${width}*${maxheight_svg}/${height}
			sed -i -e "/^<svg/s+height=\"$height\"+height=\"${maxheight_svg}\"+" ${svg}
			sed -i -e "/^<svg/s+width=\"${width}\"+width=\"${w}\"+" ${svg}
		fi
	fi

	if [ ${uxf} -nt ${jpeg} ]
	then
		echo "${uxf} -> ${jpeg}"
		${umlet} -action=convert -format=jpeg -filename=${uxf}

		width=$(identify -format "%[fx:w]" ${jpeg})
		height=$(identify -format "%[fx:h]" ${jpeg})

		let wdiff=$width-${maxwidth}
		let hdiff=$height-${maxheight} 
		typeset -F dpi
	
		if [ $width -gt ${maxwidth} -a $wdiff -gt $hdiff ]
		then
			dpi=$width/7
      			convert -trim -density ${dpi} -units pixelsperinch ${jpeg} x.jpeg
      			mv x.jpeg ${jpeg}
		fi
		if [ $height -gt ${maxheight} -a $hdiff -ge $wdiff ]
		then
			dpi=$height/10
      			convert  -trim -density ${dpi} -units pixelsperinch ${jpeg} x.jpeg
      			mv x.jpeg ${jpeg}
		fi
	fi
done

exit

# find max width and height
#
maxwidth=0
maxheight=0
for jpeg in *.jpeg
do
	width=$(identify -format "%[fx:w]" ${jpeg})
	height=$(identify -format "%[fx:h]" ${jpeg})

	if [ $height -gt $maxheight ]
	then
		maxheight=$height
	fi

	if [ $width -gt $maxwidth ]
	then
		maxwidth=$width
	fi
done

typeset -F dpi
dpi=$maxwidth/7
echo "maxwidth = $maxwidth maxheight = $maxheight dpi = $dpi"

#for png in *.png
#do
#      convert -density ${dpi} -units pixelsperinch ${png} x.png
#      mv x.png ${png}
#done

for jpeg in *.jpeg
do
      convert -density ${dpi} -units pixelsperinch ${jpeg} x.jpeg
      mv x.jpeg ${jpeg}
done
