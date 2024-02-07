;LR_PORTS
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
	bl 		pp2
	bl 		PP3
	b	m
pp1						
	ldr r0, =0x400a8000	;baza A 
	ldr r1, =0x400b0000	;baza B 
	ldr r2, =0x400b8000	;baza C
	ldr r3, =0x400c8000	;baza E
	ldr r4, =0x40020000	;baza RST_CLK 
	ldr r5, = tab	;baza tab
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
	ldrb r7, [r1];		data PortB
	tsts r7, #0x40;maska bit6-right
	andeq	r12,  #0xfd;bit1=0
	tsts 	r7, #0x20;maska bit5-up
	orreq	r12, #0x01;bit0=1
	ldrb	r7, [r3];data PortE
	tsts	r7,  #0x02;maska bit1-down
	andeq	r12, #0xfe;bit0=0
	tsts	r7,  #0x08;maska bit3-left
	orreq	r12, #0x02;bit1=1
	str 	r12, [r2]; 	ou t vd1, vd2
	bx    lr	
PP3
	tsts	r12,  #0x01 
	addeq	 r11,  #1; counter
	addne	 r11,  #-1
	mov r7, r11, lsl #1
	and r7, #0x1f;massiv-index 	
	add r7, r5;	adr	in taab
	ldrh r10, [r7]; f(n)
	tsts	r12,  #0x02 	
	streq  r10, [r0]
	streq	r10, [r6,#0x08]
	strne  r11, [r0]
	strne	r11, [r6,#0x08]	
	mov r9, #0xff
tau	subs r9, #1
	bpl tau
	bx    lr	
tab	dcw 1000,1382,1708,1924	
	dcw 2000,1924,1708,1382 
	dcw 1000,618,292,76	
	dcw 0,76,292,618		
		END
