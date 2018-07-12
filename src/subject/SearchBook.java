/*
 * @author O.toya
 * @version 1.0
 * */

package subject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class SearchBook extends JFrame implements ActionListener {

	/*** フィールド ***/
	// レイアウト領域
	//private BoxLayout box;
	private GridBagLayout gbl;

	// ラベル
	private JLabel 	searchLabel,
					resultLabelLeft,
					resultLabelRight,
					titleLabel,
					authorLabel,
					publisherLabel,
					yearLabel,
					isbnLabel;

	// テキストフィールド
	private JTextField 	searchTextField,
						titleTextField,
						authorTextField,
						publisherTextField,
						yearTextField,
						isbnTextField;

	// ボタン
	private JButton searchButton,
					saveButton,
					rightButton,
					leftButton;

	// フレームの表示領域
	private Container cntnr;

	// インスタンス化したデータを入れておく
	private static SearchBook _sb;
	private static BookDB _bookdb;

	// 表示する番号
	private int index;

	// 検索結果を管理する
	private List<Book> result = new ArrayList<>();

	public SearchBook (String title) {
		// タイトルを指定
		super(title);
	}

	public static void main (String[] args) {
		// インスタンス化
		_sb = new SearchBook("Search Book");
		_bookdb = new BookDB();

		// 準備
		_sb.init();

		// レイアウトの設定
		_sb.setMyLayout();

		// 表示
		_sb.setVisible(true);
	}

	/**
	 * 初期化処理
	 */
	public void init () {
		this.index = 0;

		// ウィンドウのサイズを指定
		setSize(640, 360);

		// ウィンドウのリサイズを禁止
		setResizable(false);

		// ウィンドウの中央に表示
		setLocationRelativeTo(null);

		// 「×」ボタンを押した時の挙動
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * レイアウト関係
	 */
	public void setMyLayout () {
		cntnr = getContentPane();
		gbl = new GridBagLayout();
		cntnr.setLayout(gbl);
		cntnr.setBackground(new Color(250, 250, 250));


		searchLabel = new JLabel("検索語: ");
		searchTextField = new JTextField();
		searchButton = new JButton("検索");

		resultLabelLeft = new JLabel("結果: ");
		resultLabelRight = new JLabel("0 / 0");

		titleLabel = new JLabel("タイトル: ");
		titleTextField = new JTextField();

		authorLabel = new JLabel("著者: ");
		authorTextField = new JTextField();

		publisherLabel = new JLabel("出版社: ");
		publisherTextField = new JTextField();

		yearLabel = new JLabel("出版年: ");
		yearTextField = new JTextField();

		isbnLabel = new JLabel("ISBN: ");
		isbnTextField = new JTextField();

		leftButton = new JButton("◀");
		rightButton = new JButton("▶");
		saveButton = new JButton("保存");

		resultLabelLeft.setHorizontalAlignment(JLabel.RIGHT);
		resultLabelRight.setHorizontalAlignment(JLabel.LEFT);

		addElement(searchLabel, 0, 0, 1, 1);
		addElement(searchTextField, 1, 0, 3, 1);

		addElement(resultLabelLeft, 1, 1, 1, 1);
		addElement(resultLabelRight, 2, 1, 1, 1);

		addElement(titleLabel, 0, 2, 1, 1);
		addElement(titleTextField, 1, 2, 3, 1);

		addElement(authorLabel, 0, 3, 1, 1);
		addElement(authorTextField, 1, 3, 3, 1);

		addElement(publisherLabel, 0, 4, 1, 1);
		addElement(publisherTextField, 1, 4, 3, 1);

		addElement(yearLabel, 0, 5, 1, 1);
		addElement(yearTextField, 1, 5, 3, 1);

		addElement(isbnLabel, 0, 6, 1, 1);
		addElement(isbnTextField, 1, 6, 3, 1);

		addElement(leftButton, 0, 7, 1, 1);
		addElement(rightButton, 1, 7, 1, 1);
		addElement(searchButton, 2, 7, 1, 1);
		addElement(saveButton, 3, 7, 1, 1);

		// 検索テキストフィールドのボーダー色を変更
		searchTextField.setBorder(new LineBorder(new Color(3, 169, 244), 1));

		// 検索テキストフィールド以外を選択不可にする
		titleTextField.setEditable(false);
		authorTextField.setEditable(false);
		publisherTextField.setEditable(false);
		yearTextField.setEditable(false);
		isbnTextField.setEditable(false);

		// 選択不可テキストフィールドの背景色を変更
		titleTextField.setBackground(new Color(255, 255, 255));
		authorTextField.setBackground(new Color(255, 255, 255));
		publisherTextField.setBackground(new Color(255, 255, 255));
		yearTextField.setBackground(new Color(255, 255, 255));
		isbnTextField.setBackground(new Color(255, 255, 255));

		// クリックイベントを設定
		searchButton.addActionListener(this);
		leftButton.addActionListener(this);
		rightButton.addActionListener(this);
		saveButton.addActionListener(this);
	}

	/**
	 * GridLayout 上でレイアウトを指定するメソッド
	 * @param elm 追加する要素
	 * @param x x座標
	 * @param y y座標
	 * @param w 幅
	 * @param h 高さ
	 */
	public void addElement (Component elm, int x, int y, int w, int h) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gbc.insets = new Insets(4, 10, 4, 10);
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbl.setConstraints(elm, gbc);
        cntnr.add(elm);
	}

	/**
	 *  テキストフィールドに検索したテキストを表示する
	 * @param _book 表示する Book インスタンス
	 */
	public void showResult (Book _book) {
		titleTextField.setText(_book.getTitle());
		authorTextField.setText(_book.getAuthor());
		publisherTextField.setText(_book.getPublisher());
		yearTextField.setText(_book.getYear());
		isbnTextField.setText(_book.getIsbn());
	}

	@Override
	public void actionPerformed (ActionEvent e) {
		// 検索ボタンをクリックしたとき
		if (e.getSource() == searchButton) {
			String word = searchTextField.getText();
			this.index = 0;

			if (!word.equals("")) {
				this.result = _bookdb.searchBookData(word);

				// 検索結果が null 以外なら
				if (result.size() > 0) {
					resultLabelRight.setText((index + 1) + " / " + result.size());
					_sb.showResult(result.get(0));
				}

				else {
					JOptionPane.showMessageDialog(this, new JLabel("見つかりませんでした。"));
				}
			}

			else {
				JOptionPane.showMessageDialog(this, new JLabel("検索したい単語を入力してください。"));
			}
		}

		// 左ボタンをクリックしたとき
		else if (e.getSource() == leftButton && index > 0) {
			index--;
			resultLabelRight.setText((index + 1) + " / " + result.size());
			_sb.showResult(result.get(index));
		}

		// 右ボタンをクリックしたとき
		else if (e.getSource() == rightButton && index < (result.size() - 1) && result.size() != 0) {
			index++;
			resultLabelRight.setText((index + 1) + " / " + result.size());
			_sb.showResult(result.get(index));
		}

		// 保存ボタン
		else if (e.getSource() == saveButton) {
			// 保存が行える状態か
			if (result.size() > 0) {
				// 保存ができたかを入れる
				boolean isSave = _bookdb.saveResult(result.get(index));

				// 保存後のアラート表示
				if (isSave) {
					JOptionPane.showMessageDialog(this, new JLabel("保存が完了しました。"));
				}

				else {
					JOptionPane.showMessageDialog(this, new JLabel("保存に失敗しました。"));
				}
			}

			else {
				JOptionPane.showMessageDialog(this, new JLabel("先に検索を行ってください。"));
			}
		}
	}
}
