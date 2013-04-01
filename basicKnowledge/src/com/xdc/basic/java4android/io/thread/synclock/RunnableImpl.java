package com.xdc.basic.java4android.io.thread.synclock;

class RunnableImpl implements Runnable
{
	int	i	= 100000;

	public void run()
	{
		while (true)
		{
			synchronized (this)
			{
				if (i < 0)
				{
					break;
				}
				System.out.println(Thread.currentThread().getName() + "-->" + i);
				i--;
				Thread.yield();
			}
		}
	}
}
