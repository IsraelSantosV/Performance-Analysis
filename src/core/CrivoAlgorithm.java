package core;

public class CrivoAlgorithm {
	
	private boolean[] m_Flags;
	private CrivoThread[] m_Threads;
	private int m_ThreadsAmount;
	private final int FIRST = 2;
	private int m_MaxSize;
	
	public boolean[] getFlagList(){ return m_Flags; }
	public int getThreadsAmount() { return m_ThreadsAmount; }
	
	public long start(int size, int threadsAmount) {
		//Initialization
		m_Flags = new boolean[size];
		m_MaxSize = size;
		
		for(int i = 0; i < size; i++) {
			m_Flags[i] = false;
		}
		
		m_ThreadsAmount = threadsAmount;
		
		//Execution
		var currentTime = System.nanoTime();
		
		if(m_Flags.length >= FIRST) {
			for(int i = 0; i < FIRST; i++) {
				m_Flags[i] = true; 
			}
		}
		
		m_Threads = new CrivoThread[threadsAmount];
		
		int k = FIRST;
		while(k < m_MaxSize) {
			var k_square = (int)Math.pow(k, 2);
			int maxInterval = (m_MaxSize + 1) - k_square;
			
			int usedThreads = maxInterval >= threadsAmount ? threadsAmount : maxInterval; 
			int subInterval = (int)Math.floor(maxInterval / usedThreads);
			var currentRest = maxInterval % threadsAmount;
			
			for(int i = 0; i < usedThreads; i++) {
				int startIndex = i * subInterval + k_square;
				int endIndex = (i + 1) * (int)subInterval + k_square;
				if(i == usedThreads - 1) {
					endIndex += (int) currentRest;
				}
				
				m_Threads[i] = new CrivoThread(this, startIndex, endIndex, k);
			}
			
			for(int i = 0; i < usedThreads; i++) {
				CrivoThread myThread = m_Threads[i];
				try { myThread.join(); } 
				catch (InterruptedException e) { }
			}
			
			k = getLowerValue(k);
		}

		var endTime = System.nanoTime() - currentTime;
		return endTime;
	}
	
	private int getLowerValue(int k) {
		for(int i = k+1; i < m_MaxSize; i++) {
			if(!getFlagList()[i]) {
				return i;
			}
		}
		
		return m_MaxSize;
	}
}
