/*
 * @author O.toya
 * @version 1.0
 * */

package subject;

import java.util.Map;

public class Book {
	// タイトル
	private String title;

	// 著者
	private String author;

	// 出版社
	private String publisher;

	// 出版年
	private String year;

	// ISBN
	private String isbn;

	/**
	 * コンストラクタ
	 * @param BookData 本の情報(タイトル、著者、出版社、出版年、ISBN)
	 */
	public Book (Map<String, String> bookData) {
		// 各データを保持する
		this.title = bookData.get("タイトル");
		this.author = bookData.get("著者");
		this.publisher = bookData.get("出版社");
		this.year = bookData.get("出版年");
		this.isbn = bookData.get("ISBN");
	}

	/***** ゲッター *****/
	public String getTitle () {
		return this.title;
	}

	public String getAuthor () {
		return this.author;
	}

	public String getPublisher () {
		return this.publisher;
	}

	public String getYear () {
		return this.year;
	}

	public String getIsbn () {
		return this.isbn;
	}
}
