;LR_3
	area stack, noinit, readwrite
	space 0x400	
stack_top				
	area reset, data, readonly
	dcd stack_top			
	dcd start			
	area program,code,readonly	
	entry				
start					
	bl		pp1		;
	bl		pp2		;
m	bl		pp3		;
;	bl		pp4		;
;	bl		pp5		;
	b		m		;
pp1	
	ldr r0, =0x40020000	;BazAdr RST_CLK
	ldr	r1, =0x40070000	;BazAdr Tim1
	ldr	r2, =0x40078000	;BazAdr Tim2
	ldr r3, =0x400a8000	;BazAdr Port_A 
	ldr r4, =0x400b8000	;BazAdr  Port_C
	ldr  r5,=0x400c8000	;BazAdr Port_E 
	ldr  r6,=0x40090000	;BazAdr DAC
	ldr r7,=0x0aa4c010	; per_clock
	str r7, [r0, #0x1c]	;
	ldr  r7, =0x03000000;Tim-1-2-Clk
	str r7, [r0, #0x24]
	mov	r7, #0xff		;	_port_A_
	str	r7, [r3,#0x04]	;OE	
	str	r7, [r3,#0x0c]	;Analog	
	mov r7, #0x5555		;	
	str	r7, [r3,#0x18]	;PWR
	mov	r7, #0x03		;	_Port C_ 	
    str	r5, [r4,#0x04]	;OE
	mov	r7, #0x07 ;
	str	r7, [r4,#0x0c]	;Analog	
	mov  r7, #0x05		;	
	str	r7, [r4,#0x18]	;PWR 
	mov	r7, #0x01		;   _Port E_
	str	r7, [r5,#0x04]	;OE
	movw  r7, #0xfe		;	
	str	r7, [r5,#0x0c]	;Analog	
	movw  r7, #0x5554	;	
	str	r7, [r5,#0x18]	;PWR
	mov	r7, #0x08		;	_DAC_
	str	r7, [r6]		;	
	ldr r7,=0x0aa4c010	; per_clock
	str r7, [r0, #0x1c]	;	ldr  r7, =0x03000000;Tim-1-2-Clk
	str r7, [r0, #0x24]
	
	mov  r7,  # 0		;PSG _Tim1, Tim2_
	str  r7, [r1, #0x04]; 
	str  r7, [r2, #0x04]; 
	movw  r7,  #100		;ARR
	str  r7, [r1, #0x08]; 
	str  r7, [r2, #0x08]; 
	mov  r7, #0x01		;CNTRL
	str  r7, [r1, #0x0c];
	str  r7, [r2, #0x0c];
	movw  r7, #25		;ch1_CCR	
	str  r7, [r1, #0x10];
 	str  r7, [r2, #0x10];
	movw  r7, #15		;ch2_CCR	
	str  r7, [r1, #0x14];
 	str  r7, [r2, #0x14];
	mov  r7, # 0x0c00	;CH1_CNTRL
	str  r7,[r1, #0x20]	;
	str  r7,[r2, #0x20]	;
	mov  r7, # 0x0c00	;CH2_CNTRL
	str  r7,[r1, #0x24]	;
	str  r7,[r2, #0x24]	;
	movw  r7, # 0x909	;CH1_CNTRL1;		
	str  r7, [r1, #0x30];  
	str  r7, [r2, #0x30]; 
	movw r0, #0x909		;CH2_CNTRL1;
	str  r0, [r1, #0x34];
	str  r0, [r2, #0x34];
	bx    lr
pp2	
	movw r7, #0x03b8
;	mov r7, #0		
	str   r7, [r3,#0x08]; _FUNC
	bx    lr
pp3	
	add	r11, #0x01	
	and r12, r11, 0xff
	add r12,  0x0; N(t)
	str r12,[r3]			
	str r12,[r6,#0x08]
	str r12,[r1,#0x05]
	str r11, [r2,#0x10]
	mov r9, #0xfff
tau	subs r9, #1
	bpl tau		
	bx    lr	
	END		
