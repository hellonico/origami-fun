DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

function convertone() {
	filename=$1
	extension="${filename##*.}"
	base="${filename%.*}"

	$DIR/url2pdf --print-orientation Portrait --print-paginate YES --enable-javascript YES --url \
  "http://localhost:10000/worksheet.html?filename=gorillas/$filename" \
  --autosave-name $base.pdf --autosave-path ./pdf
}

for i in `ls gorillas` ; do
  convertone $i
done
