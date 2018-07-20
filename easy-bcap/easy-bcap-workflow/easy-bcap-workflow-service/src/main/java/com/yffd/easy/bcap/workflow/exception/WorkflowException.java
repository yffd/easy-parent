package com.yffd.easy.bcap.workflow.exception;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2018年7月18日 下午6:04:30 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class WorkflowException extends RuntimeException {
	private static final long serialVersionUID = 282270374641263552L;

	public WorkflowException() {
        super();
    }

    public WorkflowException(String message) {
        super(message);
    }

    public WorkflowException(Throwable cause) {
        super(cause);
    }

    public WorkflowException(String message, Throwable cause) {
        super(message, cause);
    }
    
}

