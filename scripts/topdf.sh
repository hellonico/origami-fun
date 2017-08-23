DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

function convertone() {
	filename=$1
	extension="${filename##*.}"
	base="${filename%.*}"

  echo "Deleting previous file";
  rm -f ./pdf/$base.pdf

	$DIR/url2pdf --print-orientation Portrait --print-paginate YES --enable-javascript YES --url \
  "http://localhost:10000/worksheet.html?filename=gorillas/$filename" \
  --autosave-name $base.pdf --autosave-path ./pdf
}

function convertall() {
  for i in `ls gorillas` ; do
    convertone $i
  done
}

if [ -z ${1+x} ]; then
  echo "Generating all pdfs";
  convertall
else
  echo "Generating one pdf: '$1'";
  convertone $1
fi
