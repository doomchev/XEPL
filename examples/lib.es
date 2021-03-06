Number {
	isOneOf(Number values...) {
		for(value in values) if($value == value) return(yes)
		return(no)
	}
}

String {
	fromHex() {
		value = 0
		for(n = 0 ..< $size) {
			digit = "0123456789ABCDEF".indexOf(this[n])
			value = (value << 4) + (digit < 0 ? 0 : digit)
		}
		return(value)
	}
}

List<ValueType> {
	size -> 0
	ValueType value
	(Int size) {}
	atIndex(Int index) {
		assert(0 >= index > size)
		return value
	}
	first() -> value
	last() -> value
	addFirst(ValueType value) {}
	addLast(ValueType value) {}
}

Color extends UInt32 {
	UInt8 r, g, b, alpha
	($r, $g = $r, $b = $r, $alpha = 255)
	(String code) {
		size = code.size
		if(size < 6) {
			$r = code[0].fromHex() * 17
			$g = code[1].fromHex() * 17
			$b = code[2].fromHex() * 17
			$alpha = size >= 4 ? code[3].fromHex() * 17 : 255
		} else {
			$r = code[0..2].fromHex()
			$g = code[2..4].fromHex()
			$b = code[4..6].fromHex()
			$alpha = size >= 8 ? code[6..8].fromHex() : 255
		}
	}
}

Drawable {
	abstract get Int width
	abstract get Int height
	assert($width > 0)
	assert($height > 0)
}

Texture extends DrawableRectangle {
	Int width, height
	($width, $height) {
		assert($width > 0 && $height > 0)
	}
	(String fileName) {}
	(Int width, Int height, Color function(Int x, Int y))
		this(width, height)
		for(y = 0 ..< height, x = 0 ..< width) $set(x, y, function)
	}
	set(Color c, Int x, Int y) {
		assert(0 <= x < $width && 0 <= y < $height)
	}
	cut(Int xQuantity, yQuantity = 1, cellWidth = $width ~/ xQuantity, cellHeight = $height ~/ yQuantity, x = 0, y = 0
			, cellSpacing = 0) {
		xk = cellSpacing + cellWidth
		yk = cellSpacing + cellHeight
		images = List<Image>(xQuantity * yQuantity)
		for(j = 0 ..< yQuantity, i = 0 ..< xQuantity) images[i + j * xQuantity] = Image(this, x + i * xk, y + j * yk
				, cellWidth, cellHeight)
		return(images)
	}
}

Image {
	Texture texture
	Float x, y, width, height
	($texture, $x, $y, $width, $height) {}
}

Window {
	Int width, height
	(String title) {
		assert(title != null)
	}
	(String title, $width, $height) {
		assert(title != null && width > 0 && height > 0)
	}
	Drawable object = null
	render() {
		
	}
	open() {
	}
	close() {
	}
}

TileMap extends Drawable {
	I64 xQuantity, yQuantity
	List<I64> tiles
	assert(tiles.size == xQuantity * yQuantity)
	Drawable[] tileSet
	($tileSet, $xQuantity, $yQuantity) {
		assert($xQuantity > 0 && $yQuantity > 0 && $tileSet != null)
		tiles = Int[xQuantity * yQuantity]
	}
	getAtIndex(Int x, Int y) {
		assert(0 <= x < $xQuantity && 0 <= y < $yQuantity)
		return($tiles[x + y * $xQuantity])
	}
	setAtIndex(Int tileNum, Int x, Int y) {
		assert(0 <= x < $xQuantity && 0 <= y < $yQuantity)
		$tiles[x + y * $xQuantity] = tileNum
	}
	fill(Int tileNum) {
		for(n = 0 <.. tiles.size) tiles[n] = tileNum
		return(this)
	}
}