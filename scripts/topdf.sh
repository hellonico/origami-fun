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

function toc() {
  echo "Generating TOC"
  rm -f toc.*
  grep "# " gorillas/* | awk '{ $1=""; print}' | sed 's/ //' > toc.md
  # echo '<head><link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css"></head>' > toc.html
  cat scripts/toc_header.html > toc.html
  pandoc toc.md >> toc.html 
  cupsfilter toc.html > toc.pdf 2>/dev/null
  # wkhtmltopdf -T 0 -R 0 -B 0 -L 0 --orientation Portrait --page-size --zoom 0.8 A4 toc.html toc.pdf
  rm -fr toc.md toc.html toc.txt
}

function book() {
  rm -f book.pdf
  toc
  java -jar ~/Downloads/pdfbox-app-2.0.7.jar PDFMerger toc.pdf pdf/*.pdf book.pdf
}

if [ -z ${1+x} ]; then
  convertall
  book
else
  convertone $1
  book
fi

