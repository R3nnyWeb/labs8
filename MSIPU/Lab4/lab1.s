;LR_2_3_
	area stack, noinit, readwrite;1
	space 0x400			;2
stack_top				;3
	area reset, data, readonly	;4
	dcd stack_top			;5
	dcd start			;6
	area program,code,readonly	;7
	entry				;8
start					;9
	bl		pp1		;10
;	bl		pp2		;11
	bl		pp3		;12
m	add	r5, #0x01	;13 N(t)
	str r5,[r2]			;14
	b		m		;15
pp1					;16
	ldr r1,=0x4002001c	;17_per_
	ldr r0,=0x0a24c010	;18_clock__
	str r0,[r1]		;19
	ldr  r2,=0x400a8000 	;20_PortA__
	mov  r0, #0xff	;21 
	str   r0, [r2,#0x04]	;22_OE
	str   r0, [r2,#0x0c]	;23_ANALOG
	movw r0, #0x00	;24 
	str    r0, [r2,#0x08]	;25_FUNC
	movw  r0, #0xaaaa	;26_PWR   
	str   r0, [r2,#0x18]	;27   
	ldr  r3,=0x400c8000	;28_Port_E__	
	mov  r0, #0x01	;29 
	str   r0, [r3,#0x04]	;320OE
	mov  r0, #0x0a	;27
	str   r0, [r3,#0x0c]	;28_ANALOG
	mov  r0,  #0x44	;29
	str  r0, [r3,#0x18]	;30_FUNC
	ldr  r4,=0x40090000	;31__DAC_
	mov	r0, #0x08	;32
	str	r0,   [r4]	;33
	bx  lr			;34
pp2		
	ldr  r5, =0x40020024 	;35 TIM1_
	ldr  r0, =0x01000000 	;36_CLOC
	str  r0, [r5]		;37
	ldr  r6, =0x40070000  ;38_baz_addr_
	mov  r0,  # 50000		;39_Tim1
	str  r0,[r6,  #0x04 ]	;40 PSG
	movw  r0,  #100	;41
	str  r0, [r6, #0x08]	;42 ARR
	mov  r0, #0x41	;43
	str  r0, [r6, #0x0c]	;44 CNTRL 
	movw  r0, #40		;45	
	str  r0, [r6, #0x10]	;46 CCR1
	movw  r0, #50		;47
	str  r0, [r6, #0x14]	;48 CCR2
	mov  r0, # 0x0c00	;49
	str  r0,[r6, #0x20]	;50 CH1_CNTRL
	mov  r0, # 0x0c00	;51
	str  r0,[r6, #0x24]	;52 CH2_CNTRL
	movw  r0, # 0x909	;53	
	str  r0, [r6, #0x30] 	;54 CH1_CNTRL1;	

	movw r0, #0x909	;55 
	str  r0, [r6, #0x34] 	;56 CH2_CNTRL1;
	movw r0, #0x02a8	;57
	str   r0,  [r2,#0x08]	;58_FUNC_port_A
	bx    lr			;59
pp3	
	ldr  r5, =0x40020024 	;60  TIM1_
	ldr  r0, =0x03000000 	;61 _CLOC
	str  r0, [r5]		;62 
	ldr  r6, =0x40070000  ;63 _baz_addr_
	mov  r0, # 0		;64 _Tim1
	str  r0,[r6,#0x04 ]	;65  PSG
	movw  r0, #10		;66
	str  r0, [r6, #0x08]	;67  ARR
	mov  r0, #0x01	;68 
	str  r0, [r6, #0x0c]	;69  CNTRL 	
	ldr  r7, =0x40078000  ;70 _baz_addr_
	mov  r0, # 0		;71 _Tim2
	str  r0,[r7,#0x04 ]	;72  PSG
	movw  r0, #50		;73 
	str  r0, [r7, #0x08]	;74  ARR
	mov  r0, #0x01	;75 
	str  r0, [r7, #0x0c]	;76  CNTRL
	movw  r0, #1		;77 	
	str  r0, [r6, #0x10]	;78  CCR1
	movw  r0, #25		;79 
	str  r0, [r7, #0x14]	;80  CCR2
	mov  r0, # 0x0c00	;81 
	str  r0,[r6, #0x20]	;82  CH1_CNTRL
	mov  r0, # 0x0c00	;83 
	str  r0,[r7, #0x24]	;84  CH2_CNTRL
	movw  r0, # 0x909	;85 	
	str  r0, [r6, #0x30] 	;86  CH1_CNTRL1;
	movw r0, #0x909	;87  
	str  r0, [r7, #0x34] 	;88  CH2_CNTRL1;
	movw r0, #0x03e8	;89 	
	str   r0, [r2,#0x08]	;90 _FUNC_port_A
	bx    lr			;91
	END		;92 
