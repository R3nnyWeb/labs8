;		LR_1__ports
	area stack, noinit, readwrite
	space 0x400	
stack_top	
	area reset, data, readonly
	area program,code,readonly
	dcd stack_top
	dcd start		
	entry		
start
	bl		pp1	
m
	;7bl 		pp2
	bl 		pp3
	b	m
pp1						
	ldr r0, =0x400a8000	;baza A 
	ldr r1, =0x400b0000	;baza B 
	ldr r2, =0x400b8000	;baza C
	ldr r3, =0x400c8000	;baza E
	ldr r4, =0x40020000	;baza RST_CLK 
	ldr r6, =0x40090000	;baza DAC 
	
	ldr r7, =0x0be60010 ; _PER_CLOCK_	
	str r7,[r4,#0x1c] 	;
	
	mov	r7, #0xff;       	 _PortA_ 	
	str	r7, [r0,#0x04];OE	
	str	r7, [r0,#0x0c];Analog	
	mov r7, #0x5555;	
	str	r7, [r0,#0x18];PWR
	
	mov	r7, #0x00;		  _Port B_	
	str	r7, [r1,#0x04];OE
	mov	r7, #0xff ;
	str	r7, [r1,#0x0c];Analog
	
	mov	r7, #0x03; 	      _Port C_ 	
    str	r5, [r2,#0x04];OE
	mov	r7, #0x07 ;
	str	r7, [r2,#0x0c];Analog	
	mov  r7, #0x55;	
	str	r7, [r2,#0x18];PWR
	mov	r7, #0x01;   _Port E_
	str	r7, [r3,#0x04];OE
	movw  r7, #0xfe;	
	str	r7, [r3,#0x0c];Analog	
	movw  r7, #0x5554;	
	str	r7, [r3,#0x18];PWR
				
	mov	r7, #0x08;	_DAC_
	str	r7, [r6];	cfg..
	bx    lr
pp2 
	mov r10, #0x1
	str  r10, [r0]
m1	
	bl tau
	eor r10, #0x2
	str r10, [r0]
	bl tau
	eor r10, #0x1
	str r10, [r0]
	bl tau 
	eor r10, #0x4
	str r10, [r0]
	b m1
	
pp3
	push {lr}
	bl point
	bl line
	bl point
	bl tau
	pop {lr}
	bx lr

point
	push {lr}
	bl invertBit
	bl tau
	bl invertBit
	bl tau
	pop {lr}
	bx lr

line
	push {lr}
	bl invertBit
	bl tau
	bl tau
	bl invertBit
	bl tau
	pop {lr}
	bx lr
invertBit
	ldr r10, [r0]
	eor r10, #0x1
	str r10, [r0]
	bx lr

tau	
	mov r9, #20000
tau1
	subs r9, #1
	bpl tau1
	bx    lr	

	end