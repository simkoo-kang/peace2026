/**
 * CryptoApp.java
 */
package kr.pe.cipher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import kr.pe.util.Sys;
import kr.pe.util.cipher.AES;
import kr.pe.util.rnd.RandomStringGenerator;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 17.
 */
public class CryptoApp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1321564899020919897L;

	// GUI 컴포넌트 변수
	private JTextField inputField;
	private JTextField outputField;
	private JTextField upperCountField;
	private JTextField lowerCountField;
	private JTextField specialCountField;
	private JTextField numCountField;

	private JButton encryptButton;
	private JButton decryptButton;
	private JButton autoGenerateButton;
	private JButton exitButton;

	/**
	 * <pre></pre>
	 * 
	 * @throws HeadlessException
	 */
	public CryptoApp() {
		// 1. 창(Frame) 기본 설정
		setTitle("암·복호화 및 암호 생성 시스템");
		setSize(550, 320);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));

		// 2. 입력란 및 출력란 디자인 배치 (메인 패널)
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel ioPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		// 입력 문자열 구성
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.15;
		ioPanel.add(new JLabel("입력 문자열:"), gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.85;
		this.inputField = new JTextField();
		ioPanel.add(this.inputField, gbc);

		// 결과 문자열 구성
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.15;
		ioPanel.add(new JLabel("결과 문자열:"), gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.85;
		this.outputField = new JTextField();
		this.outputField.setEditable(false);
		this.outputField.setBackground(Color.LIGHT_GRAY);
		ioPanel.add(this.outputField, gbc);

		mainPanel.add(ioPanel);
		mainPanel.add(Box.createVerticalStrut(10));

		// 3. 조건 설정 컨트롤러 패널 배치
		JPanel conditionPanel = new JPanel(new GridLayout(2, 4, 10, 5));
		conditionPanel.setBorder(BorderFactory.createTitledBorder("자동 생성 암호 조건 (개수 입력)-자동생성에만 적용"));

		conditionPanel.add(new JLabel("영문 대문자:", SwingConstants.RIGHT));
		this.upperCountField = new JTextField("1");
		conditionPanel.add(this.upperCountField);

		conditionPanel.add(new JLabel("영문 소문자:", SwingConstants.RIGHT));
		this.lowerCountField = new JTextField("6");
		conditionPanel.add(this.lowerCountField);

		conditionPanel.add(new JLabel("특수 문자:", SwingConstants.RIGHT));
		this.specialCountField = new JTextField("1");
		conditionPanel.add(this.specialCountField);

		conditionPanel.add(new JLabel("숫자 개수:", SwingConstants.RIGHT));
		this.numCountField = new JTextField("3");
		conditionPanel.add(this.numCountField);

		mainPanel.add(conditionPanel);
		add(mainPanel, BorderLayout.CENTER);

		// 4. 조작 버튼 정렬 (하단 패널)
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		this.encryptButton = new JButton("암호화 🔒");
		this.decryptButton = new JButton("복호화 🔓");
		this.autoGenerateButton = new JButton("조건 자동 생성 ⚡");
		this.exitButton = new JButton("종료 ❌");

		buttonPanel.add(this.encryptButton);
		buttonPanel.add(this.decryptButton);
		buttonPanel.add(this.autoGenerateButton);
		buttonPanel.add(this.exitButton);

		add(buttonPanel, BorderLayout.SOUTH);

		// 결과창(outputField)에 마우스 클릭 이벤트 등록
		this.outputField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String resultText = CryptoApp.this.outputField.getText();

				// 결과창이 비어있거나 초기 안내 문구인 경우는 복사하지 않음
				if (resultText.isEmpty() || resultText.startsWith("[연동필요]")) {
					return;
				}

				// 시스템 클립보드에 문자열 복사 실행
//				StringSelection stringSelection = new StringSelection(resultText);
//				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//				clipboard.setContents(stringSelection, null);
				Sys.setStringToClipboard(resultText);
				// 사용자에게 복사가 완료되었음을 팝업으로 알림
				JOptionPane.showMessageDialog(CryptoApp.this, "암호문이 클립보드에 자동으로 복사되었습니다!", "복사 완료",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		// 5. 버튼별 액션 리스너 이벤트 매핑 (★이 부분에 기존 유틸리티를 연결합니다)
		setupListeners();
	}

	/**
	 * <pre></pre>
	 */
	private void setupListeners() {
		// [암호화 버튼 액션]
		this.encryptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String plainText = CryptoApp.this.inputField.getText();
				if (plainText.isEmpty()) {
					JOptionPane.showMessageDialog(CryptoApp.this, "입력란이 비어 있습니다.", "알림", JOptionPane.WARNING_MESSAGE);
					return;
				}
				String encoded = aesEncrypt(plainText);
				CryptoApp.this.outputField.setText(encoded);
			}
		});

		// [복호화 버튼 액션]
		this.decryptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String cipherText = CryptoApp.this.inputField.getText();
				if (cipherText.isEmpty()) {
					JOptionPane.showMessageDialog(CryptoApp.this, "복호화할 암호문을 입력란에 넣어주세요.", "알림",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				String decoded = aesDecrypt(cipherText);
				CryptoApp.this.outputField.setText(decoded); // 테스트용 데이터
			}
		});

		// [조건 자동 생성 버튼 액션]
		this.autoGenerateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int upper = Integer.parseInt(CryptoApp.this.upperCountField.getText().trim());
					int lower = Integer.parseInt(CryptoApp.this.lowerCountField.getText().trim());
					int special = Integer.parseInt(CryptoApp.this.specialCountField.getText().trim());
					int num = Integer.parseInt(CryptoApp.this.numCountField.getText().trim());
					if (upper < 0 || lower < 0 || special < 0 || num < 0) {
						throw new NumberFormatException();
					}
					String encoded = generateRandomString(upper, lower, special, num);
					CryptoApp.this.inputField.setText(encoded); // 테스트용 임시 출력
					CryptoApp.this.outputField.setText("");
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(CryptoApp.this, "조건 입력란에는 0 이상의 숫자만 입력해주세요.", "입력 오류",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// 4. [종료 버튼 액션]
		this.exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	// --- 비즈니스 로직: AES-256 암호화 함수 ---
	private String aesEncrypt(String plainText) {
		try {
			return AES.aesCBCEncode(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Error";
	}

	// --- 비즈니스 로직: AES-256 복호화 함수 ---
	private String aesDecrypt(String cipherText) {
		try {
			return AES.aesCBCDecode(cipherText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Error";
	}

	// --- 비즈니스 로직: 무작위 문자열 생성 함수 ---
	private String generateRandomString(int upper, int lower, int special, int num) {
//		RandomStringGenerator generator = 
		int length = upper + lower + special + num;
		String randomString = RandomStringGenerator.getRandomPassword(length, upper, num, special);
		System.out.println(randomString + " " + randomString.length());
		return randomString;
	}

	// 메인 함수 실행
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new CryptoApp().setVisible(true);
			}
		});
	}
}
