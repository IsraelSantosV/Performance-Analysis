package core;

public class CrivoThread extends Thread {
	
	private int m_StartIndex;
	private int m_MaxSize;
	private int m_Multiple;
	private CrivoAlgorithm m_Crivo;
	
	public CrivoThread(CrivoAlgorithm crivo, int startIndex, int finalIndex, int multiple) {
		m_Crivo = crivo;
		m_StartIndex = startIndex;
		m_MaxSize = finalIndex;
		m_Multiple = multiple;
	}
	
	public void run() {
		//Running Thread
		setFlags();
	}
	
	private void setFlags() {
		for(int i = m_StartIndex; i < m_MaxSize; i++) {
			if(i % m_Multiple == 0) {
				m_Crivo.getFlagList()[i] = true;
			}
		}
	}

}
