/**
 * SearchFrame.java
 */
package kr.pe.color;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;

import kr.pe.color.base.ColorItem;
import kr.pe.color.base.ColorTable;
import kr.pe.color.base.Colors;
import kr.pe.util.LogUtil;
import kr.pe.util.Str;
import kr.pe.util.Sys;
import kr.pe.util.io.FileUtil;
import kr.pe.util.iter.Iter;
import kr.pe.util.iter.StrIter;
import kr.pe.util.ui.Toast;
import kr.pe.util.xml.Jaxb;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 18.
 */
public class SearchFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6860880895443307361L;

	private JTextField searchField; // 텍스트 입력란
	private JTextField resultField; // 텍스트 입력란
	private JButton searchButton; // 검색 버튼
	private JButton addButton; // [추가] 버튼
	private JButton clearButton; // [입력란 비우기] 버튼
	private JButton deleteButton; // [삭제] 버튼
	private JList<String> listBox; // 텍스트 리스트박스
	private DefaultListModel<String> listModel; // 리스트 데이터 모델 (여기에 추가/삭제를 수행)

	private JButton saveButton; // ⭐ [추가] 저장 버튼

	private String filename;
	private Logger logger;

	private boolean isUpdated = false;

	public SearchFrame(String xmlfilename) {
		this.logger = LogUtil.getLogger(getClass());
		this.filename = xmlfilename;

		// 1. 창(Frame) 기본 설정
		setTitle("색상 목록 관리기");
		setSize(600, 350);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));

//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeApplication();
			}
		});

		// 2. 상단 레이아웃 (검색/입력란 + 추가/삭제 버튼 그룹)
		JPanel northPanel = new JPanel(new BorderLayout());
		northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

		// 2-1. [첫 번째 줄 구성] 입력창 + 버튼들 한 줄로 배치
		JPanel firstLinePanel = new JPanel(new BorderLayout(5, 0));

		this.searchField = new JTextField(); // 화살표가 가리키는 텍스트 입력창
		firstLinePanel.add(this.searchField, BorderLayout.CENTER); // 입력창이 남는 왼쪽 공간을 꽉 채우도록 설정

		// 오른쪽 버튼들을 한데 묶을 패널 (3개의 버튼이 동일한 크기로 우측 정렬)
		JPanel topButtonGroup = new JPanel(new GridLayout(1, 3, 5, 0));
		this.searchButton = new JButton("검색");
		this.addButton = new JButton("추가");
		this.deleteButton = new JButton("선택 삭제");

		topButtonGroup.add(this.searchButton);
		topButtonGroup.add(this.addButton);
		topButtonGroup.add(this.deleteButton);

		firstLinePanel.add(topButtonGroup, BorderLayout.EAST); // 버튼 그룹을 우측 끝에 딱 붙임

		// 2-2. [두 번째 줄 구성] 저장 버튼 + Clear 버튼
		JPanel thirdLinePanel = new JPanel(new GridLayout(1, 2, 5, 0));
		this.saveButton = new JButton("💾 변경사항 XML에 저장하기");
		this.saveButton.setBackground(new Color(230, 245, 255));
		thirdLinePanel.add(this.saveButton); // 첫 번째 column

		JPanel thirdRightPanel = new JPanel(new GridLayout(1, 3));
		this.clearButton = new JButton("Clear");
		thirdRightPanel.add(this.clearButton);

		JButton reloadButton = new JButton("Reload");
		thirdRightPanel.add(reloadButton);

		JButton closeButton = new JButton("종료");
		thirdRightPanel.add(closeButton);

		thirdLinePanel.add(thirdRightPanel);

		JPanel secondLinePanel = new JPanel(new GridLayout(1, 1, 5, 0));
		this.resultField = new JTextField(); // 화살표가 가리키는 텍스트 입력창
//		this.resultField.setEditable(false); // 💡 사용자가 직접 타이핑하지 못하도록 '읽기 전용'으로 세팅하면 좋습니다.
		this.resultField.setBackground(new Color(245, 245, 245)); // 연한 회색 배경으로 결과창 느낌 강조
		secondLinePanel.add(this.resultField);

		// 2-3. 메인 상단 패널에 첫째 줄과 둘째 줄을 차례대로 누적 탑재
		JPanel containerPanel = new JPanel(new GridLayout(3, 1, 0, 5));
		containerPanel.add(firstLinePanel);
		containerPanel.add(thirdLinePanel);
		containerPanel.add(secondLinePanel);

		northPanel.add(containerPanel, BorderLayout.NORTH);

		// 3. 중앙 영역 구성 (텍스트 리스트박스 + 스크롤)
		this.listModel = new DefaultListModel<>();
		// ⭐️ [핵심 추가] 초기 설정: 보관 중인 XML 파일을 읽어서 리스트 모델에 세팅
		loadData();

		this.listBox = new JList<>(this.listModel);
		this.listBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(this.listBox);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10),
				BorderFactory.createLineBorder(Color.LIGHT_GRAY)));

		// 4. 컴포넌트 배치
		add(northPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

		// 5. 이벤트 리스너 연결

		// [리스너 1] 리스트박스 아이템 클릭 시 입력란에 표시
		this.listBox.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					String selectedValue = SearchFrame.this.listBox.getSelectedValue();
					if (selectedValue != null) {
						SearchFrame.this.searchField.setText(selectedValue);
					}
				}
			}
		});

		// [리스너 2] 검색 기능 (엔터 키 대응 포함)
		ActionListener searchAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				executeSearch();
			}
		};
		this.searchButton.addActionListener(searchAction);
		this.searchField.addActionListener(searchAction);

		// ⭐ [리스너 3] 동적 추가 기능
		this.addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = SearchFrame.this.searchField.getText().trim();
				if (input.isEmpty()) {
					SearchFrame.this.logger.debug("추가할 텍스트를 입력해주세요.");
					JOptionPane.showMessageDialog(SearchFrame.this, "추가할 텍스트를 입력해주세요.");
					return;
				}

				// 중복 검사 (옵션)
				if (SearchFrame.this.listModel.contains(input)) {
					SearchFrame.this.logger.debug("이미 목록에 존재하는 이름입니다.");
					JOptionPane.showMessageDialog(SearchFrame.this, "이미 목록에 존재하는 이름입니다.");
					return;
				}

				// 모델에 동적으로 데이터 추가하면 JList에 실시간 반영됨
				SearchFrame.this.listModel.addElement(input);
				SearchFrame.this.isUpdated = true;
				SearchFrame.this.searchField.setEditable(true);

				// 추가 후 편의성 기능: 스크롤을 맨 아래(새로 추가된 항목)로 이동시키고 포커스 지정
				int lastIndex = SearchFrame.this.listModel.getSize() - 1;
				SearchFrame.this.listBox.setSelectedIndex(lastIndex);
				SearchFrame.this.listBox.ensureIndexIsVisible(lastIndex);
				SearchFrame.this.searchField.setText(""); // 입력란 초기화
			}
		});

		// ⭐ [리스너 4] 동적 삭제 기능
		this.deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = SearchFrame.this.listBox.getSelectedIndex();

				// 선택된 항목이 없을 때 예외 처리
				if (selectedIndex == -1) {
					SearchFrame.this.logger.debug("삭제할 항목을 리스트에서 선택해주세요.");
					JOptionPane.showMessageDialog(SearchFrame.this, "삭제할 항목을 리스트에서 선택해주세요.");
					return;
				}

				// 모델에서 인덱스를 지정하여 삭제
				SearchFrame.this.listModel.remove(selectedIndex);
				SearchFrame.this.searchField.setText(""); // 입력란 비우기
				SearchFrame.this.isUpdated = true;
				SearchFrame.this.searchField.setEditable(true);
			}
		});

		// ⭐ [리스너 추가] 저장 버튼 클릭 시 파일 저장 수행
		this.saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (SearchFrame.this.isUpdated) {
					saveData();
				}
			}
		});

		this.clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SearchFrame.this.searchField.setText("");
				SearchFrame.this.resultField.setText("");
			}
		});

		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeApplication();
			}
		});

		reloadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(SearchFrame.this, "데이터를 다시 불러오시겠습니까?", "Reload",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (choice == JOptionPane.YES_NO_OPTION) {
					SearchFrame.this.listModel.clear();
					loadData();
				}
			}
		});

		this.resultField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				String res = SearchFrame.this.resultField.getText();
				if (Str.isEmpty(res)) {
					return;
				}
				Sys.setStringToClipboard(res);
				Toast.show(SearchFrame.this, "[" + res + "]를 클립보드에 복사하였습니다.");
			}
		});

		this.saveButton.setEnabled(false);

//		Toast.show(this, "hellow! java?");
		/**
		 * <pre>
		 * 0: Yes
		 * -1: No
		 * -2: Cancel
		 * </pre>
		 */
//		int res = JOptionPane.showConfirmDialog(this, "Yes or No");
//		int res = JOptionPane.showConfirmDialog(this, "hellow?", "greeting", JOptionPane.YES_NO_OPTION);
//		this.logger.debug("{}", res);
	}

	/**
	 * <pre></pre>
	 */
	protected void closeApplication() {
		// 1단계: 사용자 종료 의사 재확인 안내창
		int choice = JOptionPane.showConfirmDialog(this, "프로그램을 종료하시겠습니까?\n종료 전 변경사항이 모두 파일에 저장됩니다.", "종료 확인",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		// 2단계: YES를 누른 경우에만 안전 자원 백업 후 물리 종료 프로세스 가동
		if (choice == JOptionPane.YES_OPTION) {
			try {
				// 이전에 구현해 둔 XML 마셜링 저장 함수를 호출하여 무조건 백업 보장
				if (this.isUpdated) {
					choice = JOptionPane.showConfirmDialog(this, "변경 내용을 저장하시겠습니까?", "저장 확인", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (choice == JOptionPane.YES_NO_OPTION) {
						saveData();
						this.logger.debug("[시스템] 데이터 안전 백업 완료.");
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				this.logger.error("[오류] 백업 중 에러 발생: {}", ex.getMessage(), ex);
			} finally {
				// 3단계: 그래픽 리소스를 OS에 반환하고 JVM 가상머신 완전히 Down
				dispose();
				System.exit(0);
			}
		} else {
			// '아니오'를 누르면 저장이나 종료 없이 부드럽게 화면으로 복귀
			this.logger.debug("[시스템] 사용자가 종료를 취소하고 화면으로 복귀했습니다.");
		}
	}

	/**
	 * <pre></pre>
	 */
	protected void saveData() {
		try {
			// 6-1. 현재 리스트박스(listModel)의 상태를 기반으로 JAXB 도메인 객체 조립
			Colors colorsRoot = new Colors();
			ColorTable table = null;
			String prevId = null;

			for (int i = 0; i < this.listModel.getSize(); i++) {
				String colorInfo = this.listModel.getElementAt(i);
				Iter it = new StrIter("[^\\s]+");
				it.find(colorInfo);
				String id = it.next();
				String name = it.next();
				String hex = it.next();
				String rgb = it.next();
				if (prevId == null || !id.equals(prevId)) {
					table = colorsRoot.getTable(id);
					if (table == null) {
						table = new ColorTable(id);
						colorsRoot.addTable(table);
					}
					prevId = id;
				}
				ColorItem item = new ColorItem(name, hex, rgb);
				table.addColor(item);
			}

			Jaxb.convertToFile(colorsRoot, this.filename);
			this.isUpdated = false;
			this.searchButton.setEnabled(this.isUpdated);

			String msg = "변경사항이 " + this.filename + " 파일에 안전하게 저장되었습니다.";
			this.logger.debug(msg);
			JOptionPane.showMessageDialog(this, msg, "저장 완료", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			String msg = "파일 저장 도중 에러가 발생했습니다: " + e.getMessage();
			this.logger.debug(msg, e);
			JOptionPane.showMessageDialog(this, msg, "오류", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * <pre></pre>
	 */
	private void loadData() {
		if (Str.isEmpty(this.filename)) {
			this.logger.debug("filename is null!");
		}
		try {
			File file = new File(this.filename);

			// 파일이 존재하지 않는 경우 알림 후 빈 상태로 시작
			if (!file.exists()) {
				this.logger.debug("[안내] 지정된 위치에 XML 파일이 없어 빈 목록으로 시작합니다.");
				return;
			}

			// XML ➔ 자바 객체 변환
			Colors colors = Jaxb.convertFromFile(this.filename, Colors.class);

			// 자바 객체 순회하며 리스트박스 모델(listModel)에 추가
			if (colors.getTables() != null) {
				for (ColorTable table : colors.getTables()) {
					if (table.getColors() != null) {
						String id = table.getId();
						for (ColorItem color : table.getColors()) {
							String item = id + " " + color.toString();
							// JList 화면에 노출할 색상명(Name)을 추가
							this.listModel.addElement(item);
						}
					}
				}
			}
			this.logger.debug("[성공] XML 데이터를 성공적으로 불러와 초기화했습니다.");

		} catch (Exception e) {
			e.printStackTrace();
			String msg = "XML 파일을 읽는 도중 오류가 발생했습니다: " + e.getMessage();
			this.logger.debug(msg, e);
			JOptionPane.showMessageDialog(this, msg, "오류", JOptionPane.ERROR_MESSAGE);
		}
	}

	// 핵심 로직: 리스트박스에서 텍스트를 검색하여 인덱스 이동
	private void executeSearch() {
		String query = this.searchField.getText().trim().toLowerCase();

		if (query.isEmpty()) {
			this.logger.debug("검색어를 입력해주세요.");
			JOptionPane.showMessageDialog(this, "검색어를 입력해주세요.");
			return;
		}

		boolean found = false;
		// 리스트의 모든 아이템을 순회하며 매칭되는 항목 검색
		for (int i = 0; i < this.listModel.getSize(); i++) {
			String item = this.listModel.getElementAt(i).toLowerCase();

			// 부분 일치(contains) 조건 적용
			if (item.contains(query)) {
				this.listBox.setSelectedIndex(i); // 1. 해당 인덱스 선택 상태로 변경
				this.listBox.ensureIndexIsVisible(i); // 2. 스크롤을 해당 인덱스 위치로 자동 이동
				found = true;
				break; // 가장 먼저 매칭된 항목에서 멈춤
			}
		}

		if (!found) {
			String color = ColorAPIRequest.fetchColorName(query);
			String[] ss = color.split("\t");
			color = ss[0] + " " + ss[1] + " " + Str.hexToRgb(ss[1]);
			Iter it = StrIter.getStrIter(color);
			it.find(color);
			if (it.size() < 3) {
				// table id 가 없으면 자신을 table id 로
				color = it.next() + " " + color;
			}
			this.searchField.setText(color);
			this.resultField.setText(color);
			this.logger.debug("일치하는 항목이 없습니다. 인터넷으로 검색 유사 색상: " + color);
			Toast.show(this, "인터넷으로 검색 유사 색상: " + color);
//			JOptionPane.showMessageDialog(this, "일치하는 항목이 없습니다.");
		}
	}

	public static void main(String[] args) {
		String xmlfilename = FileUtil.PATH_WORKSPACE_DOCS + "/color_table.xml";
		// Swing GUI 스레드의 안전한 실행을 보장
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SearchFrame(xmlfilename).setVisible(true);
			}
		});
	}
}
