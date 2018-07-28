/*
 * @author O.toya
 * @version 1.0
 * */

package subject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDB {
	// 本のデータを格納しておく配列
	private List<Book> bookDataList = new ArrayList<>();

	public BookDB () {
		try {
			// ファイル読み込み
			BufferedReader br = new BufferedReader( new FileReader("booklist.csv") );

			String line;
			while ( (line = br.readLine()) != null ) {
				// カンマで文字列を分割
				String[] splitStr = line.split(",");


				// HashMap にデータを格納
				Map<String, String> addData = new HashMap<String, String>() {
					{
						put("タイトル", splitStr[0]);
						put("著者", splitStr[1]);
						put("出版社", splitStr[2]);
						put("出版年", splitStr[3]);
						put("ISBN", splitStr[4]);
					}
				};

				// Book インスタンス
				Book book = new Book(addData);
				bookDataList.add(book);
			}

			br.close();

		}

		// 以下 例外
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO: 初期化の動作の内容を聞く

	/**
	 * 検索を行う
	 * @param word 検索ワード
	 * @return 本のデータ (Map型)
	 */
	public List<Book> searchBookData (String word) {
		List<Book> resultList = new ArrayList<>();

		// データの検索を行う
		for (Book _book : bookDataList) {
			// 文字列を連結する
			String mergeStr = _book.getTitle() + _book.getAuthor() + _book.getPublisher() + _book.getYear() + _book.getIsbn();

			// 連結した文字列から目当てのデータがあるかチェック
			if ( mergeStr.indexOf(word) != -1 ) {
				resultList.add(_book);
			}
		}

		// データがなかった場合
		return resultList;
	}

	/**
	 * 検索結果の書き出しを行う
	 * @param savaData GUI 上に表示しているデータ
	 * @return 保存ができたかどうか (bool型)
	 */
	public boolean saveResult (List<Book> _bookList) {
		try {
			PrintWriter pw = new PrintWriter( new BufferedWriter( new FileWriter("result.csv") ) );

			// すべての検索結果を保存する
			for (Book _book: _bookList) {
				// 文字列として連結を行う
				String mergeStr = _book.getTitle() + "," + _book.getAuthor() + "," + _book.getPublisher() + "," + _book.getYear() + "," + _book.getIsbn();

				// 書き込み
				pw.println(mergeStr);
			}

			pw.close();

			// 書き込み結果成功
			return true;
		}

		catch (IOException e) {
			// 例外
			e.printStackTrace();
		}

		// 書き込み失敗
		return false;
	}
}
