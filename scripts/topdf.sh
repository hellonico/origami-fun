DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

function convertone() {
  echo "Generating pdf: '$1'";

	filename=$1
	extension="${filename##*.}"
	base="${filename%.*}"

  # echo "Deleting previous file";
  rm -f ./pdf/$base.pdf

	$DIR/url2pdf --print-orientation Portrait --print-paginate YES --enable-javascript YES --url \
  "http://localhost:10000/worksheet.html?filename=gorillas/$filename" \
  --autosave-name $base.pdf --autosave-path ./pdf > /dev/null 2>&1
}

function convertall() {
  for i in `ls gorillas` ; do
    convertone $i
  done

}

function book() {
  rm book.pdf
  java -jar ~/Downloads/pdfbox-app-2.0.7.jar PDFMerger pdf/*.pdf book.pdf
}

if [ -z ${1+x} ]; then
  convertall
  book
else
  convertone $1
  book
fi
